package com.example.dietplanapp.fragment;

import static com.example.dietplanapp.AppContext.foodMap;
import static com.example.dietplanapp.utils.realtime_search_similar.convertFileToBase64;
import static com.example.dietplanapp.utils.realtime_search_similar.getAuth;
import static com.example.dietplanapp.utils.realtime_search_similar.sendPost;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.dietplanapp.R;
import com.example.dietplanapp.activity.MySaveActivity;
import com.example.dietplanapp.activity.SetActivity;
import com.example.dietplanapp.custom.HeadDialog;
import com.example.dietplanapp.utils.LogUtils;
import com.example.dietplanapp.utils.ProgressUtils;
import com.example.dietplanapp.utils.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalyseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalyseFragment extends Fragment {
    private boolean isClickCamera;
    private String imagePath;

    public static final int RESULT_OK = -1;

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;

    private static final int CROP_SMALL_PICTURE = 2;

    //图片路径
    protected static Uri tempUri = null;
    private Bitmap selectBitmap;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tv_kcal;
    TextView tv_db;
    TextView tv_ts;
    TextView tv_zf;
    Button btn_analyse;
    TextView tv_result;

    Boolean isAnalyse=false;

    Uri uri;
    ImageView ic_tx;
    String analyseResult=null;
    Context mContext;
    public AnalyseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalyseFragment newInstance(String param1, String param2) {
        AnalyseFragment fragment = new AnalyseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_analyse, container, false);
        ic_tx=view.findViewById(R.id.ic_tx);
        ic_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK,null);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
//                startActivityForResult(intent,1);
                openHeadDialog();
            }
        });
        btn_analyse=view.findViewById(R.id.btn_analyse);
        btn_analyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnalyse){
                    tv_result.setText(analyseResult+"");
                }else{
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.tips_noanalyse),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_kcal=view.findViewById(R.id.tv_kcal);
        tv_db=view.findViewById(R.id.tv_db);
        tv_ts=view.findViewById(R.id.tv_ts);
        tv_zf=view.findViewById(R.id.tv_zf);
        tv_result=view.findViewById(R.id.tv_result);
        return  view;
    }

    private void openHeadDialog() {
        final HeadDialog headDialog = new HeadDialog(getActivity());
        headDialog.show();
        headDialog.setClicklistener(new HeadDialog.ClickListenerInterface() {
            @Override
            public void doGetCamera() {
                // 相机
                headDialog.dismiss();
                isClickCamera = true;
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openCamera();
                }
            }

            @Override
            public void doGetPic() {
                // 图库
                headDialog.dismiss();
                isClickCamera = false;
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    selectFromAlbum();
                }
            }

            @Override
            public void doCancel() {
                // 取消
                headDialog.dismiss();
            }
        });
    }

    private void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:

                    if (hasSdcard()) {
                        if (resultCode == RESULT_OK) {
                            cropPhoto(tempUri);
                        }
                    } else {
                        Toast.makeText(mContext, "没有SDCard!", Toast.LENGTH_LONG)
                                .show();
                    }
                    break;
                case CHOOSE_PICTURE:
                    Bitmap bitmap = null;

//                    startPhotoZoom(intent.getData()); // 开始对图片进行裁剪处理
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                    break;
                case CROP_SMALL_PICTURE:
                    similarSearchPic(imagePath);
                    Log.e(LogUtils.funAndLine(new Exception()),"imagepath:"+imagePath);
                    if (isClickCamera) {
                        bitmap = BitmapFactory.decodeFile(imagePath);
                        if (bitmap!=null){
//                                  bitmap = ImageUtils.toRoundBitmap(bitmap); // 这个时候的图片已经被处理成圆形的了
//                            bitmap =ImageUtils.resizeBitmap(bitmap,640,640);
                            ic_tx.setImageBitmap(bitmap);
                            selectBitmap=bitmap;
                            Log.e(LogUtils.funAndLine(new Exception())+bitmap.getByteCount(),"bitmap size :"+bitmap.getWidth()+"-"+bitmap.getHeight());
                            //saveImageToGallery("1111.jpg");

                        }

                    } else {
//                        similarSearchPic(imagePath);
                        bitmap = BitmapFactory.decodeFile(imagePath);
                        if(bitmap!=null){
                            ic_tx.setImageBitmap(bitmap);
                            selectBitmap=bitmap;
                            Log.e(LogUtils.funAndLine(new Exception())+bitmap.getByteCount(),"bitmap size :"+bitmap.getWidth()+"-"+bitmap.getHeight());

                        }
                    }
                    break;
            }
        }

//        if (requestCode == 1) {
//            //从相册返回的数据
//            if (data != null) {
//                //得到图片的全路径
//                uri = data.getData();
//                // ivPhoto.setImageURI(uri);
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getActivity().getContentResolver().query(uri,
//                        filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String path = cursor.getString(columnIndex);  //获取照片路径
//                Bitmap bitmap = BitmapFactory.decodeFile(path);
//                ic_tx.setImageBitmap(bitmap);
//                Log.e("onActivityResult","path:"+path);
//                cursor.close();
//                similarSearchPic(path);
//
//            }
//        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        imagePath = null;
        tempUri = data.getData();
        if (DocumentsContract.isDocumentUri(mContext, tempUri)) {
            //如果是document类型的uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(tempUri);
            if ("com.android.providers.media.documents".equals(tempUri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.downloads.documents".equals(tempUri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(tempUri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(tempUri, null);
        } else if ("file".equalsIgnoreCase(tempUri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = tempUri.getPath();
        }

        cropPhoto(tempUri);
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    ////////////andoird 4.4之前
    private void handleImageBeforeKitKat(Intent intent) {
        tempUri = intent.getData();
        imagePath = getImagePath(tempUri, null);
        cropPhoto(tempUri);
    }

    /**
     * 打开系统相机
     */
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 打开相机
        File oriPhotoFile = null;
        try {
            oriPhotoFile = createOriImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (oriPhotoFile != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                tempUri = Uri.fromFile(oriPhotoFile);
            } else {
                tempUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", oriPhotoFile);
            }
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 进入这儿表示没有权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    // 提示已经禁止
                    ToastUtil.show("Permission Denial");
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
                }
            } else {

                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//私有目录读写权限
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);

                startActivityForResult(intent, TAKE_PICTURE);
            }
        }
    }
    /**
     * 创建原图像保存的文件
     *
     * @return
     * @throws IOException
     */
    private File createOriImageFile() throws IOException {
        String imgNameOri = "register_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pictureDirOri = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/ResPicture");

        if (!pictureDirOri.exists()) {
            pictureDirOri.mkdirs();
        }
        File image = File.createTempFile(
                imgNameOri,         /* prefix */
                ".jpg",             /* suffix */
                pictureDirOri       /* directory */
        );
        imagePath = image.getPath();
        return image;
    }
    /**
     * 创建裁剪图像保存的文件
     *
     * @return
     * @throws IOException
     */
    private File createCropImageFile() throws IOException {
        String imgNameCrop = "register_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pictureDirCrop = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/CropPicture");
        if (!pictureDirCrop.exists()) {
            pictureDirCrop.mkdirs();
        }
        File image = File.createTempFile(
                imgNameCrop,         /* prefix */
                ".jpg",             /* suffix */
                pictureDirCrop      /* directory */
        );
        // imgPathCrop = image.getAbsolutePath();
        imagePath = image.getAbsolutePath();

        return image;
    }

    /**
     * 裁剪图片
     *
     * @param uri 需要 裁剪图像的Uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        File cropPhotoFile = null;
        try {
            cropPhotoFile = createCropImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (cropPhotoFile != null) {
            //7.0 安全机制下不允许保存裁剪后的图片
            //所以仅仅将File Uri传入MediaStore.EXTRA_OUTPUT来保存裁剪后的图像
            Uri imgUriCrop = Uri.fromFile(cropPhotoFile);

            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", true);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 640);
            intent.putExtra("outputY", 640);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriCrop);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//这个有影响
            startActivityForResult(intent, CROP_SMALL_PICTURE);
        }
    }



    public void similarSearchPic( String filePath ) {
        ProgressUtils.show(getActivity(),"loadig...");
        new Thread(new Runnable(){
            @Override
            public void run() {
                //请求详情
                // 请求url
                String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/realtime_search/similar/search";
                try {
                    // 本地文件路径
                    // String filePath = "[本地文件路径]";
                    Log.e("similarSearch","filepath:"+filePath);
                    String img_str=convertFileToBase64(filePath);
                    //对base64 utf-8转码
                    String imgParam="" ;
                    try {
                        imgParam= URLEncoder.encode(img_str, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Log.e("similarSearch","error:"+e.getMessage());
                    }
//                    byte[] imgData = FileUtil.readFileByBytes(filePath);
//                    String imgStr = Base64Util.encode(imgData);
//                    String imgParam = URLEncoder.encode(imgStr, "UTF-8");

                    String param = "image=" + imgParam ;//+ "&pn=" + 0 + "&rn=" + 100;
                    //  String param="url="+uri.toString();
                    // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
                  //  String clientId = "9pihif81J4rWdcmR0QkGkK5c";
                    String clientId = "ZxhoRiuhHWeH5W3httdoVGcF";
                    // 官网获取的 Secret Key 更新为你注册的
                  //  String clientSecret = "go0WxLVDw9dlOft2pSCu9am1HBPbJR2n";
                    String clientSecret = "ilccXvXUFFTdqFLdxImFDWr9BHmUR62o";

                    String access_token=getAuth(clientId, clientSecret);
                    String request_url=url+"?access_token="+access_token;

                    String result=sendPost(request_url,param);

//                    String result = HttpUtil.post(url, access_token, param);
                    System.out.println(result);
                    JSONObject json_test = new JSONObject(result);
                    String json_data = json_test.getString("result");
                    if(!json_data.isEmpty()) {
                        JsonArray json = new JsonParser().parse(json_data).getAsJsonArray();
                        float max_score = 0.0f;
                        String title = "";
                        for (int i = 0; i < json.size(); i++) {
                            JsonObject obj = json.get(i).getAsJsonObject(); //obj 为JSONObject数据
                            float cur_score = obj.get("score").getAsFloat();
                            if (max_score < cur_score) {
                                max_score = cur_score;
                                title = obj.get("brief").getAsString();
                            }
                        }

                        Log.e("similarSearch", "title:" + title + "|score: " + max_score);
                        Bundle bundle = new Bundle();
                        bundle.putString("title", title);
                        bundle.putFloat("score", max_score);
                        Message msg = handler.obtainMessage();
                        msg.what = 0;
                        msg.setData(bundle);
                        msg.sendToTarget();

                    }
                    // return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //  return null;
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bundle bundle = msg.getData();
                    float score=bundle.getFloat("score");
                    String title=bundle.getString("title");
                    Toast.makeText(getActivity(), "Identify result："+title+"，Similarity："+score, Toast.LENGTH_LONG).show();
                    ProgressUtils.dismiss();
                    // allCommodities.clear();
                    Log.e("handleMessage", "识别到商品："+title+"，相似度："+score);
                    tv_kcal.setText(foodMap.get(title).kcal+"KCal");
                    tv_ts.setText(foodMap.get(title).tanshui+"g");
                    tv_db.setText(foodMap.get(title).danbai+"g");
                    tv_zf.setText(foodMap.get(title).zhifang+"g");
                    analyseResult=foodMap.get(title).advise;
                    isAnalyse=true;
                    tv_result.setText("");
                    break;
            }
            super.handleMessage(msg);
        }
    };

}