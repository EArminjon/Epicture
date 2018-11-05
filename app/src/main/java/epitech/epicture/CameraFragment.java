package epitech.epicture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ImageButton;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment implements SurfaceHolder.Callback {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Camera camera;
    private int cameraMod;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private boolean previewing = false;
    LayoutInflater controlInflater = null;
    Account account;

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle == null)
            return inflater.inflate(R.layout.fragment_tab1, container, false);
        account = (Account) bundle.getSerializable("account");


        View myFragmentView = inflater.inflate(R.layout.fragment_tab1, container, false);

        ((Activity) getContext()).getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView) myFragmentView.findViewById(R.id.surfaceView2);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        /*controlInflater = LayoutInflater.from(getBaseContext());*/
        /*View viewControl = controlInflater.inflate(R.layout.control, null);
        LayoutParams layoutParamsControl
                = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        this.addContentView(viewControl, layoutParamsControl);*/

        ImageButton switchButton = (ImageButton) myFragmentView.findViewById(R.id.buttonTakePicture);
        switchButton.setOnClickListener(v -> camera.takePicture(null, null, mPictureCallback));

        ImageButton takeButton = (ImageButton) myFragmentView.findViewById(R.id.buttonSwitchCamera);
        takeButton.setOnClickListener(v -> new Thread(() -> {
            destroyCamera();
            if (cameraMod == Camera.CameraInfo.CAMERA_FACING_FRONT)
                openCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
            else
                openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
            try {
                camera.setPreviewDisplay(surfaceHolder);
                setCameraDisplayOrientation((Activity) getContext(), cameraMod, camera);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start());
        return myFragmentView;
    }

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] imageData, Camera c) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            System.out.print("PICTURE TAKEN\n");
            upload(account, bitmap);
            camera.startPreview();
        }
    };


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        if (previewing) {
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
                setCameraDisplayOrientation((Activity) this.getContext(), cameraMod, camera);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static void setCameraDisplayOrientation(Activity activity, int cameraMod, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraMod, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    private void openCamera(int mod) {
        cameraMod = mod;
        camera = Camera.open(mod);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        openCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    private void destroyCamera() {
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        destroyCamera();
    }

    private void upload(Account account, Bitmap imageBitmap) {
        ImgurApi api = new ImgurApi();

        if (account != null)
            new Thread(() -> api.postImageToAccount(getContext(), account, imageBitmap)).start();
    }
}
