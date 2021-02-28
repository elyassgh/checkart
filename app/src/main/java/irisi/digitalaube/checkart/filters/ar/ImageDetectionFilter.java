package irisi.digitalaube.checkart.filters.ar;


import android.content.Context;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint.Cap;
import android.os.Build.VERSION_CODES;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import irisi.digitalaube.checkart.adapters.CameraProjectionAdapter;
import irisi.digitalaube.checkart.api.model.Tapis;
import irisi.digitalaube.checkart.api.model.TapisFound;
import irisi.digitalaube.checkart.api.model.TapisMat;
import irisi.digitalaube.checkart.api.serviceImp.UserServiceImpl;
import irisi.digitalaube.checkart.database.CheckArtContrat.CarpetTable;
import irisi.digitalaube.checkart.database.CheckArtDbHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class ImageDetectionFilter implements ARFilter {

    private static final String TAG = "HOPE" ;
    // The reference image (this detector's target).
    private Mat mReferenceImage;
    List<Mat> mReferencesImage = new ArrayList<Mat>();
    List<TapisMat> dbReferencesImages = new ArrayList<TapisMat>();
    // Features of the reference image.
    private final MatOfKeyPoint mReferenceKeypoints =
            new MatOfKeyPoint();
    List<MatOfKeyPoint> mReferencesKeypoints = new ArrayList<MatOfKeyPoint>();
   private TapisFound tapisFound;
    // Descriptors of the reference image's features.
    private final Mat mReferenceDescriptors = new Mat();
    List<Mat> mReferencesDescriptors = new ArrayList<Mat>();
    // The corner coordinates of the reference image, in pixels.
    // CVType defines the color depth, number of channels, and
    // channel layout in the image. Here, each point is represented
    // by two 32-bit floats.
    private final Mat mReferenceCorners =
            new Mat(4, 1, CvType.CV_32FC2);
    List<Mat> mReferencesCorners = new ArrayList<Mat>();
    // The reference image's corner coordinates, in 3D, in real
    // units.
    private final MatOfPoint3f mReferenceCorners3D =
            new MatOfPoint3f();
    List<MatOfPoint3f> mReferencesCorners3D =  new ArrayList<MatOfPoint3f>();

    // Features of the scene (the current frame).
    private final MatOfKeyPoint mSceneKeypoints =
            new MatOfKeyPoint();
    // Descriptors of the scene's features.
    private final Mat mSceneDescriptors = new Mat();
    // Tentative corner coordinates detected in the scene, in
    // pixels.
    private final Mat mCandidateSceneCorners =
            new Mat(4, 1, CvType.CV_32FC2);
    // Good corner coordinates detected in the scene, in pixels.
    private final MatOfPoint2f mSceneCorners2D =
            new MatOfPoint2f();
    // The good detected corner coordinates, in pixels, as integers.
    private final MatOfPoint mIntSceneCorners = new MatOfPoint();

    // A grayscale version of the scene.
    private final Mat mGraySrc = new Mat();
    // Tentative matches of scene features and reference features.
    private final MatOfDMatch mMatches = new MatOfDMatch();

    // A feature detector, which finds features in images.
    private final FeatureDetector mFeatureDetector =
            FeatureDetector.create(FeatureDetector.ORB);
    // A descriptor extractor, which creates descriptors of
    // features.
    private final DescriptorExtractor mDescriptorExtractor =
            DescriptorExtractor.create(DescriptorExtractor.ORB);
    // A descriptor matcher, which matches features based on their
    // descriptors.
    private final DescriptorMatcher mDescriptorMatcher =
            DescriptorMatcher.create(
                    DescriptorMatcher.BRUTEFORCE_HAMMINGLUT);

    // Distortion coefficients of the camera's lens.
    // Assume no distortion.
    private final MatOfDouble mDistCoeffs = new MatOfDouble(
            0.0, 0.0, 0.0, 0.0);

    // An adaptor that provides the camera's projection matrix.
    private CameraProjectionAdapter mCameraProjectionAdapter;
    // The Euler angles of the detected target.
    private final MatOfDouble mRVec = new MatOfDouble();
    // The XYZ coordinates of the detected target.
    private final MatOfDouble mTVec = new MatOfDouble();
    // The rotation matrix of the detected target.
    private final MatOfDouble mRotation = new MatOfDouble();
    // The OpenGL pose matrix of the detected target.
    private final float[] mGLPose = new float[16];
    private  double realSize;
    // Whether the target is currently detected.
    private boolean mTargetFound = false;
         private   CheckArtDbHelper sql;
    @RequiresApi(api = VERSION_CODES.P)
    public ImageDetectionFilter(final Context context,
                                final int [] referenceImageResourceIDs,
                                final CameraProjectionAdapter cameraProjectionAdapter,
                                final double realSize)
            throws IOException {

        this.realSize = realSize;
        // Load the reference image from the app's resources.
        // It is loaded in BGR (blue, green, red) format.

         sql = new CheckArtDbHelper(context);
        sql.deleteDb();
       UserServiceImpl.getTapis().enqueue(new Callback<List<Tapis>>() {
            @Override
            public void onResponse(Call<List<Tapis>> call, Response<List<Tapis>> response) {
                if (!response.isSuccessful()) {

                    Log.i(TAG, "CodeError: " + response.code());
                    return;
                }
                List<Tapis> tapis = response.body();

                for(Tapis t: tapis){

                    Log.i(TAG, "tapis Nom "+ t.getNom());
                    Log.i(TAG, "tapis Desc "+ t.getDescription());
                    Log.i(TAG, "tapis Couleur "+ t.getCouleur());
                    Log.i(TAG, "tapis Taille "+ t.getTaille());
                    Log.i(TAG, "tapis Uri "+ t.getUri());
                    if(t.getPhoto() != null){
                        Log.i(TAG, "tapis Photo "+ t.getPhoto());

                        byte[] decodedString = Base64.decode(t.getPhoto(), Base64.DEFAULT);
                    Bitmap img = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    Mat mat_img = new Mat();
                    Utils.bitmapToMat(img, mat_img);

                 // sql.dbput(t,mat_img);
                        }

                }


            }
            @Override
            public void onFailure(Call<List<Tapis>> call, Throwable t) {
                Log.i(TAG, "failure "+ t.getMessage());

            }
        });







        Tapis tapis1 = new Tapis();
        tapis1.setNom("TAPIS MRIRT 100% LAINE");
        tapis1.setCouleur("Orange");
        tapis1.setDescription("Grand tapis en laine orange avec . Laine de grande qualité, tapis ancien, fabriqué de façon\n" +
                "artisanale par les femmes berbères de la région.");
        tapis1.setTaille(" 315x185 cm");
        tapis1.setOrigine("Origine: Mrirt");
        tapis1.setMotif("Motifs: Losange, le losange ouvert représente \nla fécondité et la maternité.");
        tapis1.setUri("azilal");
        sql.dbput(tapis1,Utils.loadResource(context,
                referenceImageResourceIDs[2],
                Imgcodecs.CV_LOAD_IMAGE_COLOR) );


        Tapis tapis2 = new Tapis();
        tapis2.setNom("GRAND TAPIS ZANAFI");
        tapis2.setCouleur("Noir, gris, blanc");
        tapis2.setDescription(" Le tapis Zanafi se distingue par ses motifs. Sa technique de fabrication se rapproche de celle du \n" +
                "Kilim marocain, à savoir tissé, ce qui permet un motif plus fin. Les tapis Zanafi ont pris le nom de la tribu \n" +
                "Zanafi, dans le Haut Atlas du Maroc.La grande dimension de ce tapis donnera une impression de grandeur à votre \n" +
                "séjour ou chambre et apportera une touche ethnique et authentique à votre intérieur. ");
        tapis2.setTaille("315x185 cm");
        tapis2.setOrigine("Origine: Haut Atlas marocain");
        tapis2.setMotif("Motis: Losange, le losange représente \nla fécondité et la maternité");
        tapis2.setUri("azilal");
        sql.dbput(tapis2,Utils.loadResource(context,
                referenceImageResourceIDs[3],
                Imgcodecs.CV_LOAD_IMAGE_COLOR) );



        Tapis tapis3 = new Tapis();
        tapis3.setNom("TAPIS BERBERE BOUJAD");
        tapis3.setCouleur("Black, White");
        tapis3.setDescription("Magnifique tapis berbère de la ville de Boujad, confectionné à la main par les femmes de cette région.\n" +
                "Les tapis Boujad sont des tapis tissés de la région du Haouz.Riches et complexes en motifs géométriques, ils ne \n" +
                "sont pas trop formels.");
        tapis3.setMotif("Motis: Lignes en forme de zig-zag, une ligne en forme de zig-zag ininterrompue cerne souvent le tapis. Pour certaines, \n" +
                "c’est le ruisseau, également représenté par une ligne continue,\n"+" le serpent ou la famille.Magnifique tapis berbère de la ville de Boujad, confectionné à la main par les femmes de cette région.\n" +
                "Les tapis Boujad sont des tapis tissés de la région du Haouz.Riches et complexes en motifs géométriques, ils ne\n" +
                "sont pas trop formels.");

        tapis3.setTaille("370x185 cm");
        tapis3.setOrigine("Origine: Boujad");
        tapis3.setUri("azilal");
        sql.dbput(tapis3,Utils.loadResource(context,
                referenceImageResourceIDs[4],
                Imgcodecs.CV_LOAD_IMAGE_COLOR) );



      /* //
        for (int i = 0; i< referenceImageResourceIDs.length; i++){

            sql.dbput(tapis,Utils.loadResource(context,
                    referenceImageResourceIDs[i],
                    Imgcodecs.CV_LOAD_IMAGE_COLOR));
            Log.i(TAG, "That Works" + i);
        }*/



        Cursor cursor = sql.findAllCarpets();
        CursorWindow cw = new CursorWindow("test", 100 * 1024 * 1024);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) cursor;
        ac.setWindow(cw);


        Log.i(TAG, "Count..." + cursor.getCount());
        if (cursor != null)
            cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            String nom = cursor.getString(cursor.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_NAME));
            String desc = cursor.getString(cursor.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_DESCRIPTION));
            String couleur = cursor.getString(cursor.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_COULEUR));
            String taille = cursor.getString(cursor.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_TAILLE));
            String origine = cursor.getString(cursor.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_ORIGINE));
            String motif = cursor.getString(cursor.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_MOTIF));
            int t = cursor.getInt(cursor.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_W1));
            int w = cursor.getInt(cursor.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_W2));
            int h = cursor.getInt(cursor.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_W3));
            byte[] p = cursor.getBlob(cursor.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_PHOTO));
            Mat m = new Mat(h,w,t);
            m.put(0,0,p);

            TapisMat tm = new TapisMat();
            tm.setNom(nom);
            tm.setDesc(desc);
            tm.setCouleur(couleur);
            tm.setTaille(taille);
            tm.setOrigine(origine);
            tm.setMotif(motif);
            tm.setMat(m);
            dbReferencesImages.add(tm);
            Log.i(TAG, "Images Mat Blobs" + m.toString());
            cursor.moveToNext();
        }


        // m = new Mat(200,400, CvType.CV_8UC3, new Scalar(0,100,0));
        //Core.putText(m, "world (~)", new Point(30,80), Core.FONT_HERSHEY_SCRIPT_SIMPLEX, 2.2, new Scalar(200,200,200));
        // sql.dbput("hello",m);
        // m2 = sql.dbget("hello");

        //Toast.makeText(getApplicationContext(), m2.toString(), Toast.LENGTH_LONG).show();

/*
        for (int i = 0; i< referenceImageResourceIDs.length; i++){
             mReferencesImage.add( Utils.loadResource(context,
                     referenceImageResourceIDs[i],
                     Imgcodecs.CV_LOAD_IMAGE_COLOR) );
        }

*/
        mCameraProjectionAdapter = cameraProjectionAdapter;
    }



    @Override
    public float[] getGLPose() {
        return (mTargetFound ? null : null);
    }





    @Override
    public TapisFound apply(final Mat src, final Mat dst) {
        mTargetFound = false;
        // Convert the scene to grayscale.
        Imgproc.cvtColor(src, mGraySrc, Imgproc.COLOR_RGBA2GRAY);

        // Detect the scene features, compute their descriptors,
        // and match the scene descriptors to reference descriptors.
        mFeatureDetector.detect(mGraySrc, mSceneKeypoints);
        mDescriptorExtractor.compute(mGraySrc, mSceneKeypoints,
                mSceneDescriptors);




//////////////Check in local
        for(int i =0; i< dbReferencesImages.size(); i++ ) {
            this.tapisFound = new TapisFound();
            mReferenceImage = dbReferencesImages.get(i).getMat();
            // Create grayscale and RGBA versions of the reference image.
            final Mat referenceImageGray = new Mat();
            Imgproc.cvtColor(mReferenceImage, referenceImageGray,
                    Imgproc.COLOR_BGR2GRAY);
            Imgproc.cvtColor(mReferenceImage, mReferenceImage,
                    Imgproc.COLOR_BGR2RGBA);


            // Store the reference image's corner coordinates, in pixels.
            mReferenceCorners.put(0, 0,
                    new double[]{0.0, 0.0});
            mReferenceCorners.put(1, 0,
                    new double[]{referenceImageGray.cols(), 0.0});
            mReferenceCorners.put(2, 0,
                    new double[]{referenceImageGray.cols(),
                            referenceImageGray.rows()});
            mReferenceCorners.put(3, 0,
                    new double[]{0.0, referenceImageGray.rows()});
            Log.i(TAG, "mReferenceCorners  " + mReferenceCorners);


            // Compute the image's width and height in real units, based
            // on the specified real size of the image's smaller
            // dimension.
            final double aspectRatio =
                    (double) referenceImageGray.cols() /
                            (double) referenceImageGray.rows();
            final double halfRealWidth;
            final double halfRealHeight;
            if (referenceImageGray.cols() > referenceImageGray.rows()) {
                halfRealHeight = 0.5f * realSize;
                halfRealWidth = halfRealHeight * aspectRatio;
            } else {
                halfRealWidth = 0.5f * realSize;
                halfRealHeight = halfRealWidth / aspectRatio;
            }


            // Define the real corner coordinates of the printed image
            // so that it normally lies in the xy plane (like a painting
            // or poster on a wall).
            // That is, +z normally points out of the page toward the
            // viewer.
            mReferenceCorners3D.fromArray(
                    new Point3(-halfRealWidth, -halfRealHeight, 0.0),
                    new Point3(halfRealWidth, -halfRealHeight, 0.0),
                    new Point3(halfRealWidth, halfRealHeight, 0.0),
                    new Point3(-halfRealWidth, halfRealHeight, 0.0));
            Log.i(TAG, "mReferenceCorners3D  " + mReferenceCorners3D);

            // Detect the reference features and compute their
            // descriptors.
            mFeatureDetector.detect(referenceImageGray,
                    mReferenceKeypoints);
            Log.i(TAG, "mReferenceKeypoints  " + mReferenceKeypoints);

            mDescriptorExtractor.compute(referenceImageGray,
                    mReferenceKeypoints, mReferenceDescriptors);
            Log.i(TAG, "mReferenceDescriptors  " + mReferenceDescriptors);
            //  mReferencesImage.add(mReferenceImage);
            //  mReferencesCorners.add(mReferenceCorners);
            // mReferencesCorners3D.add(mReferenceCorners3D);
            //mReferencesKeypoints.add(mReferenceKeypoints);
            // mReferencesDescriptors.add(mReferenceDescriptors);

            Log.i(TAG, "mReferencesImage " + mReferencesImage);
            Log.i(TAG, "mReferencesCorners " + mReferencesCorners);

            Log.i(TAG, "mReferencesCorners3D " + mReferencesCorners3D);

            Log.i(TAG, "mReferencesKeypoints " + mReferencesKeypoints);

            Log.i(TAG, "mReferencesDescriptors " + mReferencesDescriptors);


            mDescriptorMatcher.match(mSceneDescriptors,
                    mReferenceDescriptors, mMatches);

            // Attempt to find the target image's 3D pose in the scene.
            findPose();
            if(mTargetFound){
                tapisFound.setFound(true);
                tapisFound.setTapisMat(dbReferencesImages.get(i));
                return tapisFound;
            }
            tapisFound.setFound(false);
            tapisFound.setTapisMat(null);
        }
        return tapisFound;
    }

    private void findPose() {

        final List<DMatch> matchesList = mMatches.toList();
        if (matchesList.size() < 4) {
            // There are too few matches to find the pose.
            return;
        }

        final List<KeyPoint> referenceKeypointsList =
                mReferenceKeypoints.toList();
        final List<KeyPoint> sceneKeypointsList =
                mSceneKeypoints.toList();

        // Calculate the max and min distances between keypoints.
        double maxDist = 0.0;
        double minDist = Double.MAX_VALUE;
        for (final DMatch match : matchesList) {
            final double dist = match.distance;
            if (dist < minDist) {
                minDist = dist;
            }
            if (dist > maxDist) {
                maxDist = dist;
            }
        }

        // The thresholds for minDist are chosen subjectively
        // based on testing. The unit is not related to pixel
        // distances; it is related to the number of failed tests
        // for similarity between the matched descriptors.
        if (minDist > 50.0) {
            // The target is completely lost.
            mTargetFound = false;
            return;
        } else if (minDist > 25.0) {
            // The target is lost but maybe it is still close.
            // Keep using any previously found pose.
            return;
        }

        // Identify "good" keypoints based on match distance.
        final List<Point> goodReferencePointsList =
                new ArrayList<Point>();
        final ArrayList<Point> goodScenePointsList =
                new ArrayList<Point>();
        final double maxGoodMatchDist = 1.75 * minDist;
        for (final DMatch match : matchesList) {
            if (match.distance < maxGoodMatchDist) {
                goodReferencePointsList.add(
                        referenceKeypointsList.get(match.trainIdx).pt);
                goodScenePointsList.add(
                        sceneKeypointsList.get(match.queryIdx).pt);
            }
        }

        if (goodReferencePointsList.size() < 4 ||
                goodScenePointsList.size() < 4) {
            // There are too few good points to find the pose.
            return;
        }

        // There are enough good points to find the pose.
        // (Otherwise, the method would have already returned.)

        // Convert the matched points to MatOfPoint2f format, as
        // required by the Calib3d.findHomography function.
        final MatOfPoint2f goodReferencePoints = new MatOfPoint2f();
        goodReferencePoints.fromList(goodReferencePointsList);
        final MatOfPoint2f goodScenePoints = new MatOfPoint2f();
        goodScenePoints.fromList(goodScenePointsList);

        // Find the homography.
        final Mat homography = Calib3d.findHomography(
                goodReferencePoints, goodScenePoints);

        // Use the homography to project the reference corner
        // coordinates into scene coordinates.
        Core.perspectiveTransform(mReferenceCorners,
                mCandidateSceneCorners, homography);

        // Convert the scene corners to integer format, as required
        // by the Imgproc.isContourConvex function.
        mCandidateSceneCorners.convertTo(mIntSceneCorners,
                CvType.CV_32S);

        // Check whether the corners form a convex polygon. If not,
        // (that is, if the corners form a concave polygon), the
        // detection result is invalid because no real perspective can
        // make the corners of a rectangular image look like a concave
        // polygon!
        if (!Imgproc.isContourConvex(mIntSceneCorners)) {
            return;
        }

        final double[] sceneCorner0 =
                mCandidateSceneCorners.get(0, 0);
        final double[] sceneCorner1 =
                mCandidateSceneCorners.get(1, 0);
        final double[] sceneCorner2 =
                mCandidateSceneCorners.get(2, 0);
        final double[] sceneCorner3 =
                mCandidateSceneCorners.get(3, 0);
        mSceneCorners2D.fromArray(
                new Point(sceneCorner0[0], sceneCorner0[1]),
                new Point(sceneCorner1[0], sceneCorner1[1]),
                new Point(sceneCorner2[0], sceneCorner2[1]),
                new Point(sceneCorner3[0], sceneCorner3[1]));

        final MatOfDouble projection =
                mCameraProjectionAdapter.getProjectionCV();

        // Find the target's Euler angles and XYZ coordinates.
        Calib3d.solvePnP(mReferenceCorners3D, mSceneCorners2D,
                projection, mDistCoeffs, mRVec, mTVec);

        // Positive y is up in OpenGL, down in OpenCV.
        // Positive z is backward in OpenGL, forward in OpenCV.
        // Positive angles are counter-clockwise in OpenGL,
        // clockwise in OpenCV.
        // Thus, x angles are negated but y and z angles are
        // double-negated (that is, unchanged).
        // Meanwhile, y and z positions are negated.

        final double[] rVecArray = mRVec.toArray();
        rVecArray[0] *= -1.0; // negate x angle
        mRVec.fromArray(rVecArray);

        // Convert the Euler angles to a 3x3 rotation matrix.
        Calib3d.Rodrigues(mRVec, mRotation);

        final double[] tVecArray = mTVec.toArray();

        // OpenCV's matrix format is transposed, relative to
        // OpenGL's matrix format.
        mGLPose[0]  =  (float)mRotation.get(0, 0)[0];
        mGLPose[1]  =  (float)mRotation.get(0, 1)[0];
        mGLPose[2]  =  (float)mRotation.get(0, 2)[0];
        mGLPose[3]  =  0f;
        mGLPose[4]  =  (float)mRotation.get(1, 0)[0];
        mGLPose[5]  =  (float)mRotation.get(1, 1)[0];
        mGLPose[6]  =  (float)mRotation.get(1, 2)[0];
        mGLPose[7]  =  0f;
        mGLPose[8]  =  (float)mRotation.get(2, 0)[0];
        mGLPose[9]  =  (float)mRotation.get(2, 1)[0];
        mGLPose[10] =  (float)mRotation.get(2, 2)[0];
        mGLPose[11] =  0f;
        mGLPose[12] =  (float)tVecArray[0];
        mGLPose[13] = -(float)tVecArray[1]; // negate y position
        mGLPose[14] = -(float)tVecArray[2]; // negate z position
        mGLPose[15] =  1f;

        mTargetFound = true;
    }

    protected void draw(final Mat src, final Mat dst) {



        if (dst != src) {
            src.copyTo(dst);
        }
        //Log.d("test" , mTargetFound + "");
        if (mTargetFound) {
            // The target has not been found.

            // Draw a thumbnail of the target in the upper-left
            // corner so that the user knows what it is.

            // Compute the thumbnail's larger dimension as half the
            // video frame's smaller dimension.
            int height = mReferenceImage.height();
            int width = mReferenceImage.width();
            final int maxDimension = Math.min(dst.width(),
                    dst.height()) / 2;
            final double aspectRatio = width / (double)height;
            if (height > width) {
                height = maxDimension;
                width = (int)(height * aspectRatio);
            } else {
                width = maxDimension;
                height = (int)(width / aspectRatio);
            }

            // Select the region of interest (ROI) where the thumbnail
            // will be drawn.
            final Mat dstROI = dst.submat(0, height, 0, width);

            // Copy a resized reference image into the ROI.
            Imgproc.resize(mReferenceImage, dstROI, dstROI.size(),
                    0.0, 0.0, Imgproc.INTER_AREA);
        }
    }
}
