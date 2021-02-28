package ir.tdaapp.diako.shaar.FragmentPage;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gun0912.tedbottompicker.TedBottomPicker;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Adapter.List_Image_Home;
import ir.tdaapp.diako.shaar.Adapter.TypeHomeAdapter;
import ir.tdaapp.diako.shaar.Data.DA_Add_Home;
import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.ETC.CompressImage;
import ir.tdaapp.diako.shaar.ETC.FileManger;
import ir.tdaapp.diako.shaar.ETC.Font;
import ir.tdaapp.diako.shaar.ETC.GetRandom;
import ir.tdaapp.diako.shaar.ETC.Policy_Volley;
import ir.tdaapp.diako.shaar.ETC.SaveImageToMob;
import ir.tdaapp.diako.shaar.ETC.Stack_Back;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.ETC.Validation;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.Model.Location;
import ir.tdaapp.diako.shaar.Model.Type_Home;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.ViewModel.VM_ItemFeature;

import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.math.BigInteger;
import java.util.ArrayList;

import java.util.List;


import static android.app.Activity.RESULT_OK;
import static androidx.core.content.FileProvider.getUriForFile;
//import static android.support.v4.content.FileProvider.getUriForFile;


/**
 * Created by Diako on 5/6/2019.
 */

public class Fragment_Add_Home extends Fragment implements IBase {

  public static final String TAG = "Fragment_Add_Home";
  List<String> imagesEncodedList;
  RequestQueue requestQueue;
  JsonObjectRequest AddRequest;


  RadioButton rdo_Sell, rdo_Rent;
  EditText txt_AdTitle, txt_Discription, txt_Price, txt_TheArea, txt_Mobile, txt_Phone, txt_VideoLink, txt_Rent, txt_Mortgage;
  TextView lbl_Location, lbl_Property_details, lbl_Price, lbl_TheArea, lbl_DiscriptionCall, lbl_VideoLink, lbl_Rent, lbl_Mortgage;
  Spinner Spinner_VideoLink;
  RecyclerView recyclerView, recyclerView_add_img;
  Button AfterToSave, btn_Done;
  RelativeLayout Property_Home;
  RadioButton rdo_Residential, rdo_Commercial, rdo_Industrial;
  TypeHomeAdapter typeHomeAdapter_Residential, typeHomeAdapter_Commercial, typeHomeAdapter_Industrial;
  DBAdapter dbAdapter;
  GridLayoutManager gridLayoutManager;
  ImageView Add_IMG_Home;
  List_Image_Home img_homes;
  LinearLayoutManager linearLayoutManager_img;
  //برای فشرده سازی عکس
  CompressImage compressImage;
  Spinner cmb_AgeHome, cmb_CountRoom;
  SearchableSpinner cmb_Location;
  ArrayList<Location> locations;
  TextView lbl_CodePosty, lbl_Address;
  ScrollView scroll;
  //برای گرفتن id هنگامی که کاربر یک گزینه را در cmb_Area انتخاب می کند
  int locationId;
  EditText txt_CodePosty, txt_Address;
  ProgressBar progressBar;
  RelativeLayout backall;
  Validation validation;
  EditText lbl_Mobile, lbl_Phone;
  Font font;
  FileManger _FileManger;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_add_home, container, false);
    FindItem(view);
    dbAdapter = new DBAdapter(getActivity());

    int HaveAccount = new User(getActivity()).GetUserId();

    if (HaveAccount == 0) {
      Toast.makeText(getContext(), "ابتدا یک حساب کاربری ایجاد کنید", Toast.LENGTH_SHORT).show();
      ((AppCompatActivity) getActivity()).getSupportFragmentManager().
        beginTransaction().replace(R.id.Fragment_Main, new Fragment_Login_Home()).commit();
    }

    gridLayoutManager = new GridLayoutManager(getActivity(), 2);

    AdapterTypeHome(view);

    SetVal();


    recyclerView_add_img.setLayoutManager(linearLayoutManager_img);


    SetSpinnerData();

    TextChange();
    OnClick();

    Property_Home.setVisibility(View.GONE);

    font.SetFont("font/b_homa.ttf", lbl_Phone);
    font.SetFont("font/b_homa.ttf", lbl_Mobile);

    //این کد زمانی که کاربر در صفحه املاک من دکمه ویرایش را می زند در اینجا عکس های آن خانه set می شوند
    if (DA_Add_Home.ItemId != 0) {
      SetImage();
    }


    return view;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    if (AddRequest != null) {
      requestQueue = Volley.newRequestQueue(getContext());

      AddRequest.setTag(TAG);

      requestQueue.add(AddRequest);

      if (requestQueue != null) {
        requestQueue.cancelAll(TAG);
      }
    }

  }

  //در اینجا آداپتر های recycler مربوط به مسکونی صنعتی تجاری ساخته می شوند
  void AdapterTypeHome(View view) {
    typeHomeAdapter_Residential = new TypeHomeAdapter(getActivity(), SetDataTypeHome(1), view, dbAdapter);
    typeHomeAdapter_Commercial = new TypeHomeAdapter(getActivity(), SetDataTypeHome(2), view, dbAdapter);
    typeHomeAdapter_Industrial = new TypeHomeAdapter(getActivity(), SetDataTypeHome(3), view, dbAdapter);
  }

  public void SetBackGroundTientEditText(final EditText txt, final TextView lbl) {
    txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

      @Override
      public void onFocusChange(View view, boolean b) {
        if (b) {
          txt.getBackground().setColorFilter(getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorCenteral), PorterDuff.Mode.SRC_ATOP);
          lbl.setTextColor(getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorCenteral));
        } else {
          txt.getBackground().setColorFilter(getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorPaleBlack), PorterDuff.Mode.SRC_ATOP);
          lbl.setTextColor(getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
        }
      }
    });
  }


  void FindItem(View view) {
    rdo_Sell = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_RadioButton_Sell);
    rdo_Rent = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_RadioButton_Rent);
    txt_AdTitle = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_txt_AdTitle);
    txt_Discription = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_txt_DiscriptionHome);
    txt_Price = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_txt_Price);
    txt_TheArea = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_txt_TheArea);
    txt_Mobile = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_txt_Mob);
    txt_Phone = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_txt_Phone);
    txt_VideoLink = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_txt_VideoLink);
    Spinner_VideoLink = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_Spinner_VideoLink);
    txt_Rent = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_txt_Rent);
    txt_Mortgage = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_txt_Mortgage);
    cmb_Location = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_cmb_Location);

    lbl_Location = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_lbl_Location);
    lbl_Property_details = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_lbl_Property_details);
    lbl_Price = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_lbl_Price);
    lbl_TheArea = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_lbl_TheArea);
    lbl_DiscriptionCall = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_lbl_DiscriptionCall);
    lbl_VideoLink = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_lbl_AddVideo);
    AfterToSave = view.findViewById(ir.tdaapp.diako.shaar.R.id.AfterToSave);
    Property_Home = view.findViewById(ir.tdaapp.diako.shaar.R.id.Property_Home);
    lbl_Rent = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_lbl_Rent);
    lbl_Mortgage = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_lbl_Mortgage);
    Add_IMG_Home = view.findViewById(ir.tdaapp.diako.shaar.R.id.add_img_Home);
    recyclerView_add_img = view.findViewById(ir.tdaapp.diako.shaar.R.id.recycler_add_img);
    btn_Done = view.findViewById(ir.tdaapp.diako.shaar.R.id.btn_Done);
    cmb_AgeHome = view.findViewById(ir.tdaapp.diako.shaar.R.id.AgeHome);
    scroll = view.findViewById(ir.tdaapp.diako.shaar.R.id.scroll);
    txt_CodePosty = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_CodePosty);
    txt_Address = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_Address);
    lbl_CodePosty = view.findViewById(ir.tdaapp.diako.shaar.R.id.lbl_CodePosty);
    lbl_Address = view.findViewById(ir.tdaapp.diako.shaar.R.id.lbl_Address);
    lbl_Mobile = view.findViewById(R.id.lbl_Mobile);
    lbl_Phone = view.findViewById(R.id.lbl_Phone);

    SetBackGroundTientEditText(txt_AdTitle, lbl_Property_details);
    SetBackGroundTientEditText(txt_Discription, lbl_Property_details);
    SetBackGroundTientEditText(txt_Price, lbl_Price);
    SetBackGroundTientEditText(txt_TheArea, lbl_TheArea);
    SetBackGroundTientEditText(txt_Mobile, lbl_DiscriptionCall);
    SetBackGroundTientEditText(txt_Phone, lbl_DiscriptionCall);
    SetBackGroundTientEditText(txt_VideoLink, lbl_VideoLink);
    SetBackGroundTientEditText(txt_Rent, lbl_Rent);
    SetBackGroundTientEditText(txt_Mortgage, lbl_Mortgage);
    SetBackGroundTientEditText(txt_CodePosty, lbl_CodePosty);
    SetBackGroundTientEditText(txt_Address, lbl_Address);

    cmb_CountRoom = view.findViewById(ir.tdaapp.diako.shaar.R.id.cmb_CountRoom);
    recyclerView = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Add_Home_Recycler);

    img_homes = new List_Image_Home(getActivity());
    linearLayoutManager_img = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

    rdo_Commercial = view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Commercial);
    rdo_Industrial = view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Industrial);
    rdo_Residential = view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Residential);

    txt_Rent.setVisibility(View.GONE);
    lbl_Rent.setVisibility(View.GONE);
    backall = view.findViewById(ir.tdaapp.diako.shaar.R.id.backall);

    txt_Mortgage.setVisibility(View.GONE);
    lbl_Mortgage.setVisibility(View.GONE);
    compressImage = new CompressImage(320, 450, 75, getActivity());
    progressBar = view.findViewById(ir.tdaapp.diako.shaar.R.id.loadingdata_progress);
    progressBar.setVisibility(View.GONE);

    locations = new ArrayList<>();
    validation = new Validation();
    font = new Font(getContext());

    lbl_Phone = view.findViewById(R.id.lbl_Phone);
    lbl_Mobile = view.findViewById(R.id.lbl_Mobile);
  }


  public List<Type_Home> SetDataTypeHome(int type) {
    List<Type_Home> lst = new ArrayList<>();
    int Id;
    if (type == 1) {
      Cursor cursor = dbAdapter.ExecuteQ("select * from TblType where ParentId=1");
      if (cursor != null && cursor.moveToFirst()) {
        while (!cursor.isAfterLast()) {
          Id = Integer.parseInt(cursor.getString(0));

          if (Id != 37 && Id != 29 && Id != 38 && Id != 18)
            lst.add(new Type_Home(cursor.getString(1), Id));
          cursor.moveToNext();
        }
      }
      cursor.close();
      dbAdapter.close();
      return lst;
    } else if (type == 2) {
      Cursor cursor = dbAdapter.ExecuteQ("select * from TblType where ParentId=2");
      if (cursor != null && cursor.moveToFirst()) {
        while (!cursor.isAfterLast()) {
          Id = Integer.parseInt(cursor.getString(0));

          if (Id != 40 && Id != 24 && Id != 41)
            lst.add(new Type_Home(cursor.getString(1), Id));
          cursor.moveToNext();
        }
      }
      cursor.close();
      dbAdapter.close();
      return lst;
    } else {
      Cursor cursor = dbAdapter.ExecuteQ("select * from TblType where ParentId=32");
      if (cursor != null && cursor.moveToFirst()) {
        while (!cursor.isAfterLast()) {
          Id = Integer.parseInt(cursor.getString(0));
          if (Id != 36)
            lst.add(new Type_Home(cursor.getString(1), Id));
          cursor.moveToNext();
        }
      }
      cursor.close();
      dbAdapter.close();
      return lst;
    }
  }

  void OnClick() {

    backall.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Stack_Back.MyStack_Back.Pop(getContext());
      }
    });
    AfterToSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Stack_Back.MyStack_Back.Pop(getActivity());
      }
    });

    Property_Home.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Stack_Back.MyStack_Back.Push("Fragment_Property_Home", getActivity());
      }
    });

    rdo_Residential.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(typeHomeAdapter_Residential);
        progressBar.setVisibility(View.GONE);
      }
    });

    rdo_Commercial.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(typeHomeAdapter_Commercial);
        progressBar.setVisibility(View.GONE);
      }
    });

    rdo_Industrial.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(typeHomeAdapter_Industrial);
        progressBar.setVisibility(View.GONE);
      }
    });

    rdo_Sell.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        txt_Rent.setVisibility(View.GONE);
        lbl_Rent.setVisibility(View.GONE);

        txt_Mortgage.setVisibility(View.GONE);
        lbl_Mortgage.setVisibility(View.GONE);

        txt_Price.setVisibility(View.VISIBLE);
        lbl_Price.setVisibility(View.VISIBLE);

//                txt_Area.requestFocus();
      }
    });

    rdo_Rent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        txt_Rent.setVisibility(View.VISIBLE);
        lbl_Rent.setVisibility(View.VISIBLE);

        txt_Mortgage.setVisibility(View.VISIBLE);
        lbl_Mortgage.setVisibility(View.VISIBLE);

        txt_Price.setVisibility(View.GONE);
        lbl_Price.setVisibility(View.GONE);

//                txt_Area.requestFocus();
      }
    });

    Add_IMG_Home.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        selectImage();
      }
    });

    btn_Done.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        SaveImageAndDataToServer();
      }
    });

    AfterToSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LaterSave();
      }
    });
  }

  private void selectImage() {
    Dexter.withActivity(getActivity()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(
      new PermissionListener() {
        @Override
        public void onPermissionGranted(PermissionGrantedResponse response) {
          onGrantedMemory();
        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse response) {

        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
          Toast.makeText(getActivity(), R.string.Permission, Toast.LENGTH_LONG).show();
        }
      }
    ).check();

  }

  private void onGrantedMemory() {
    final CharSequence[] options = {"دوربین", "از گالری", "انصراف"};
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle((Html.fromHtml("<font color='#FF7F27'>عکس مورد نظر</font>")));
    builder.setItems(options, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int item) {
        if (options[item].equals("دوربین")) {

          Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(
            new MultiplePermissionsListener() {
              @Override
              public void onPermissionsChecked(MultiplePermissionsReport report) {
                String fileName = "temp.jpg";
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                  startActivityForResult(takePictureIntent, 1);
                }
              }

              @Override
              public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

              }
            }
          ).check();


        } else if (options[item].equals("از گالری")) {
          Dexter.withActivity(getActivity()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(
            new PermissionListener() {
              @Override
              public void onPermissionGranted(PermissionGrantedResponse response) {
                List<Uri> selectedUriList = new ArrayList<>();
                TedBottomPicker.with(getActivity())
                  .setPeekHeight(1600)
                  .showTitle(false)
                  .setCompleteButtonText("ثبت")
                  .setTitle("عکس های خود را انتخاب کنید")
                  .setEmptySelectionText("عکسی انتخاب نشده")
                  .setSelectedUriList(selectedUriList)
                  .showMultiImage(uriList -> {

                    if (DA_Add_Home.Images == null)
                      DA_Add_Home.Images = new ArrayList<>();

                    for (int i = 0; i < uriList.size(); i++) {
                      String imagePath = uriList.get(i).getPath();
                      Bitmap b = compressImage.Compress(imagePath);
                      String Name = String.valueOf(GetRandom.GetLong()) + ".jpg";
                      img_homes.Add(b);
                      SetImageHomes();
                      DA_Add_Home.Images.add(SaveImageToMob.SaveImageToSdCard(Name, b));
                    }

                    Toast.makeText(getActivity(), "Selected: " + uriList.size(), Toast.LENGTH_SHORT).show();
                  });
              }

              @Override
              public void onPermissionDenied(PermissionDeniedResponse response) {

              }

              @Override
              public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

              }
            }
          ).check();


        } else if (options[item].equals("انصراف")) {
          dialog.dismiss();
        }
      }
    });
    builder.show();
  }

  // در اینجا هنگامی که کاربر یک عکس را انتخاب می کند در recycler قرار داده می شود و سپس در حافظه ذخیره می شود و آدرس آن در DA_Add_Home.Images قرار داده می شود
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == 1) {
        Uri selectedImage = getCacheImagePath("temp.jpg");
        try {
          getActivity().getContentResolver().notifyChange(selectedImage, null);
          ContentResolver cr = getActivity().getContentResolver();
          Bitmap bitmap;
          bitmap = android.provider.MediaStore.Images.Media
            .getBitmap(cr, selectedImage);
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
          Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

          Bitmap b = compressImage.Compress(decoded);
          img_homes.Add(b);
          SetImageHomes();

          if (DA_Add_Home.Images == null)
            DA_Add_Home.Images = new ArrayList<>();

          String Name = String.valueOf(GetRandom.GetLong()) + ".jpg";
          DA_Add_Home.Images.add(SaveImageToMob.SaveImageToSdCard(Name, b));

        } catch (Exception e) {
          new AlertDialog.Builder(getContext()).setTitle((Html.fromHtml("<font style='color:#0094ff'>خطا</font>")))
            .setMessage((Html.fromHtml("<font style='color:#0094ff'>باشه</font>"))).setPositiveButton((Html.fromHtml("<font style='color:#0094ff'>خطای رخ داده است لطفا حافظه گوشی خود را چک نمایید</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
          }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
      } else if (requestCode == 2) {
        //با استفاده از کتابخانه tedbottompicker تنظیم شده است
      }
    }
  }

  private Uri getCacheImagePath(String fileName) {
    File path = new File(getActivity().getExternalCacheDir(), "camera");
    if (!path.exists()) path.mkdirs();
    File image = new File(path, fileName);
    return getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", image);
  }

  void SetImageHomes() {
    recyclerView_add_img.setAdapter(img_homes);
  }

  void TextChange() {


    ///////////////////////////////
    txt_AdTitle.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_AdTitle = txt_AdTitle.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    txt_Discription.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_DiscriptionHome = txt_Discription.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    txt_Price.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_Price = txt_Price.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    txt_Rent.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_Rent = txt_Rent.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    txt_Mortgage.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_Mortgage = txt_Mortgage.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    txt_TheArea.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_TheArea = txt_TheArea.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    txt_Mobile.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_Mob = txt_Mobile.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    txt_Phone.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_Phone = txt_Phone.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    txt_VideoLink.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_VideoLink = txt_VideoLink.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    txt_Address.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_Address = txt_Address.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    txt_CodePosty.addTextChangedListener(new TextWatcher() {

      // the user's changes are saved here
      public void onTextChanged(CharSequence c, int start, int before, int count) {
        DA_Add_Home.txt_CodePosty = txt_CodePosty.getText().toString();
      }

      public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        // this space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // this one too
      }
    });
    ///////////////////////////////

    rdo_Sell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        boolean checked = rdo_Sell.isChecked();

        if (checked == true) {
          DA_Add_Home.rdo_Sell = true;
          DA_Add_Home.rdo_Rent = false;
        } else {

          DA_Add_Home.rdo_Sell = false;
          DA_Add_Home.rdo_Rent = true;
        }
      }
    });
    ////////////////////////////////

    rdo_Rent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        boolean checked = rdo_Rent.isChecked();
        if (checked == true) {
          DA_Add_Home.rdo_Sell = false;
          DA_Add_Home.rdo_Rent = true;
        } else {
          DA_Add_Home.rdo_Sell = true;
          DA_Add_Home.rdo_Rent = false;
        }
      }
    });
    ////////////////////////////////

    rdo_Residential.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        DA_Add_Home.rdo_Residential = rdo_Residential.isChecked();
      }
    });
    ////////////////////////////////

    rdo_Commercial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        DA_Add_Home.rdo_Commercial = rdo_Commercial.isChecked();
      }
    });
    ////////////////////////////////

    rdo_Industrial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        DA_Add_Home.rdo_Industrial = rdo_Industrial.isChecked();
      }
    });
    ////////////////////////////////

    cmb_Location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Location location = (Location) parent.getSelectedItem();
        locationId = location.getId();
        DA_Add_Home.cmb_Location = cmb_Location.getSelectedItemPosition();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });

    cmb_AgeHome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        DA_Add_Home.cmb_Year_of_construction = position;
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });

    cmb_CountRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        DA_Add_Home.CountRoom = position;
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
  }

  //در این مقادیر کلاس استاتیک DA_Add_Home در المنت های موجود set می شوند
  void SetVal() {
    rdo_Sell.setChecked(DA_Add_Home.rdo_Sell);
    rdo_Rent.setChecked(DA_Add_Home.rdo_Rent);
    rdo_Residential.setChecked(DA_Add_Home.rdo_Residential);
    rdo_Commercial.setChecked(DA_Add_Home.rdo_Commercial);
    rdo_Industrial.setChecked(DA_Add_Home.rdo_Industrial);

    txt_TheArea.setText(DA_Add_Home.txt_TheArea);
    txt_AdTitle.setText(DA_Add_Home.txt_AdTitle);
    txt_Discription.setText(DA_Add_Home.txt_DiscriptionHome);
    txt_Price.setText(DA_Add_Home.txt_Price);
    txt_Rent.setText(DA_Add_Home.txt_Rent);
    txt_Mortgage.setText(DA_Add_Home.txt_Mortgage);
    txt_Mobile.setText(DA_Add_Home.txt_Phone);
    txt_Phone.setText(DA_Add_Home.txt_Mob);
    txt_VideoLink.setText(DA_Add_Home.txt_VideoLink);
    txt_Address.setText(DA_Add_Home.txt_Address);
    txt_CodePosty.setText(DA_Add_Home.txt_CodePosty);


    //در اینجا چک می شود که میان مسکونی و تجاری و صنعتی کدام تیک خورده است که آداپتر مربوط به آن در Recycler نمایش داده می شود
    if (rdo_Residential.isChecked()) {
      recyclerView.setLayoutManager(gridLayoutManager);
      recyclerView.setAdapter(typeHomeAdapter_Residential);
    }
    if (rdo_Commercial.isChecked()) {
      recyclerView.setLayoutManager(gridLayoutManager);
      recyclerView.setAdapter(typeHomeAdapter_Commercial);
    }
    if (rdo_Industrial.isChecked()) {
      recyclerView.setLayoutManager(gridLayoutManager);
      recyclerView.setAdapter(typeHomeAdapter_Industrial);
    }

    if (rdo_Sell.isChecked()) {
      lbl_Price.setVisibility(View.VISIBLE);
      txt_Price.setVisibility(View.VISIBLE);

      lbl_Rent.setVisibility(View.GONE);
      txt_Rent.setVisibility(View.GONE);

      lbl_Mortgage.setVisibility(View.GONE);
      txt_Mortgage.setVisibility(View.GONE);
    } else {
      lbl_Price.setVisibility(View.GONE);
      txt_Price.setVisibility(View.GONE);

      lbl_Rent.setVisibility(View.VISIBLE);
      txt_Rent.setVisibility(View.VISIBLE);

      lbl_Mortgage.setVisibility(View.VISIBLE);
      txt_Mortgage.setVisibility(View.VISIBLE);
    }

    if (DA_Add_Home.Images != null) {

      for (int i = 0; i < DA_Add_Home.Images.size(); i++) {
        Bitmap bmp;
        File f = new File(DA_Add_Home.Images.get(i));
        if (f.exists()) {
          bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
          img_homes.Add(bmp);
          SetImageHomes();
        }
      }
    }
  }

  //در اینجا مقادیر در اسپینر set می شوند
  void SetSpinnerData() {

    //در اینجا مقادیر اسپینر لینک set می شوند
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.ListVideoApp, R.layout.support_simple_spinner_dropdown_item);
    adapter.setDropDownViewResource(R.layout.spinner_layout);
    Spinner_VideoLink.setAdapter(adapter);

    //در اینجا مقادیر اسپینر موقعیت set می شوند


    Cursor cursor = dbAdapter.ExecuteQ("select * from TblLocation ORDER BY Title");
    if (cursor != null && cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        locations.add(new Location(Integer.parseInt(cursor.getString(0)), cursor.getString(1)));
        cursor.moveToNext();
      }
    }
    cursor.close();
    dbAdapter.close();

    ArrayAdapter<Location> adapter2 = new ArrayAdapter<Location>(getActivity(), R.layout.spinner_layout, locations);
    cmb_Location.setAdapter(adapter2);
    cmb_Location.setTitle(getResources().getString(R.string.SelectedLocation));
    cmb_Location.setPositiveButton(getResources().getString(R.string.close));

    //در ابنجا در کلاس DA_Add_Home مقدار پوزیشن را دریافت کرده و اسپینر را با آن مقدار set کرده
    cmb_Location.setSelection(DA_Add_Home.cmb_Location);

    //در اینجا مقادیر اسپینر سال ساخت set می شوند

    List<String> lstAgeHome = new ArrayList<>();

    for (int i = 1405; i >= 1300; i--) {
      lstAgeHome.add(String.valueOf(i));
    }

    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, lstAgeHome);
    cmb_AgeHome.setAdapter(adapter3);

    if (DA_Add_Home.cmb_Year_of_construction != -1)
      cmb_AgeHome.setSelection(DA_Add_Home.cmb_Year_of_construction);
    else
      cmb_AgeHome.setSelection(0);

    //برای set کردن اسپینر تعداد اتاق

    String[] CountRoomData = new String[10];
    for (int i = 0; i < CountRoomData.length; i++) {
      if (i == 0) {
        CountRoomData[i] = "بدون اتاق";
      } else {
        CountRoomData[i] = String.valueOf(i);
      }
    }

    ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, CountRoomData);
    cmb_CountRoom.setAdapter(adapter4);

    cmb_CountRoom.setSelection(DA_Add_Home.CountRoom);
  }

  //برای ذخیره کردن اطلاعات در سمت سرور
  void SaveData(List<String> NameImage, final ProgressDialog progress) {

    //در اینجا متغیر های لازم برای اطلاعات ایجا شده است

    String Price;
    String Mortgage;

    final String Title = txt_AdTitle.getText().toString();

    //متراژ
    final String Area = txt_TheArea.getText().toString();
    final String CellPhone = txt_Mobile.getText().toString();
    final String Tel = txt_Phone.getText().toString();
    final String VideoUrl = txt_VideoLink.getText().toString();
    String Discription = txt_Discription.getText().toString();


    //تعداد اتاق
    final int Room;
    if (DA_Add_Home.CountRoom == 0)
      Room = 0;
    else {
      if (cmb_CountRoom.getVisibility() == View.VISIBLE)
        Room = Integer.parseInt(cmb_CountRoom.getItemAtPosition(DA_Add_Home.CountRoom).toString());
      else
        Room = 0;
    }
    final String Age;
    if (DA_Add_Home.cmb_Year_of_construction != -1) {
      if (cmb_AgeHome.getVisibility() == View.VISIBLE)
        Age = cmb_AgeHome.getItemAtPosition(DA_Add_Home.cmb_Year_of_construction).toString();
      else {
        Age = "0";
      }
    } else {
      Age = "0";
    }
    final String Code = txt_CodePosty.getText().toString();

    final String Address = txt_Address.getText().toString();

    //برای فروش و اجاره فروش:1 و اجاره:0
    final int enumTarget;

    if (DA_Add_Home.rdo_Rent == true) {
      enumTarget = 0;
      if (txt_Rent.getText().toString().equalsIgnoreCase(""))
        Price = "0";
      else
        Price = txt_Rent.getText().toString();

      if (txt_Mortgage.getText().toString().equalsIgnoreCase(""))
        Mortgage = "0";
      else
        Mortgage = txt_Mortgage.getText().toString();
    } else {
      enumTarget = 1;
      if (txt_Price.getText().toString().equalsIgnoreCase(""))
        Price = "0";
      else
        Price = txt_Price.getText().toString();

      Mortgage = "0";
    }

    //برای وضعیت آیتم در حال انتظار:0 رد شده:1 تایید شده:2
    final int enumKind = 0;

    final int FkType = DA_Add_Home.TypeHome;

    final int FkLocation = locationId;

    final int FkUser = new User(getActivity()).GetUserId();

    final List<VM_ItemFeature> itemFeatures = new ArrayList<>();


    //در اینجا ویژگی های منزل set می شوند
    if (DA_Add_Home.booleans != null) {
      for (int i = 0; i < DA_Add_Home.booleans.size(); i++) {
        String Val = DA_Add_Home.booleans.get(i).getVal().toString();
        int Id = DA_Add_Home.booleans.get(i).getId();
        itemFeatures.add(new VM_ItemFeature(Val, Id, 2));
      }
    }

    if (DA_Add_Home.selects != null) {
      for (int i = 0; i < DA_Add_Home.selects.size(); i++) {
        String Val = DA_Add_Home.selects.get(i).getVal().toString();
        int Id = DA_Add_Home.selects.get(i).getId();
        itemFeatures.add(new VM_ItemFeature(Val, Id, 4));
      }
    }

    if (DA_Add_Home.numbers != null) {
      for (int i = 0; i < DA_Add_Home.numbers.size(); i++) {
        String Val = String.valueOf(DA_Add_Home.numbers.get(i).getVal());
        int Id = DA_Add_Home.numbers.get(i).getId();
        itemFeatures.add(new VM_ItemFeature(Val, Id, 3));
      }

    }

    if (DA_Add_Home.texts != null) {
      for (int i = 0; i < DA_Add_Home.texts.size(); i++) {
        String Val = String.valueOf(DA_Add_Home.texts.get(i).getVal());
        int Id = DA_Add_Home.texts.get(i).getId();
        itemFeatures.add(new VM_ItemFeature(Val, Id, 1));
      }
    }

    //در اینجا یک Array Json تعریف می شود که به ازای هر ویژگی Json Object ساخته شده و JsonObject در ArrayJson ذخیره می شود
    JSONArray ArrayItemFeatures = new JSONArray();

    try {

      for (int i = 0; i < itemFeatures.size(); i++) {

        //در اینجا به ازای هر ویژگی یک json object ساخته می شود
        JSONObject j = new JSONObject();
        j.put("Value", itemFeatures.get(i).getValue());
        j.put("FkFeatures", itemFeatures.get(i).getFkFeatures());
        j.put("FkFormat", itemFeatures.get(i).getFkFormat());

        //در اینجا jsonObject ساخته شده در JsonArray ذخیره می شود
        ArrayItemFeatures.put(j);
      }

    } catch (Exception ex) {

    }

    //در اینجا عکس های مربوطه به صورت Base64 در این لیست ذخیره می شوند
    final List<String> Images = new ArrayList<>();

//                for (int i = 0; i < img_homes.GetValues().size(); i++) {
//                    Images.add(Base64Image.BitmapToBase64(img_homes.GetValues().get(i)));
//                }

    for (String i : NameImage) {
      Images.add(i);
    }

    //در اینجا هم مانند ویژگی های ملک عکس ها JsonObject و سپس JsonArray ذخیره می شوند
    JSONArray ArrayImage = new JSONArray();
    try {
      for (int i = 0; i < Images.size(); i++) {
        JSONObject j = new JSONObject();
        j.put("ImageUrl", Images.get(i));
        ArrayImage.put(j);
      }
    } catch (Exception ex) {
    }

    if (FkUser != 0) {
      try {
        String URL = Api + "Item/PostNewItem";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("Title", Title);
        jsonBody.put("Price", Price);
        jsonBody.put("Area", Area);
        jsonBody.put("CellPhone", CellPhone);
        jsonBody.put("Tel", Tel);
        jsonBody.put("Room", Room);
        jsonBody.put("Age", Age);
        jsonBody.put("Code", Code);
        jsonBody.put("Address", Address);
        jsonBody.put("enumTarget", enumTarget);
        jsonBody.put("enumKind", enumKind);
        jsonBody.put("FkType", FkType);
        jsonBody.put("FkLocation", FkLocation);
        jsonBody.put("FkUser", FkUser);
        jsonBody.put("TblItemImagesTemp", ArrayImage);
        jsonBody.put("TblItemFeatuersSend", ArrayItemFeatures);
        jsonBody.put("VideoUrl", VideoUrl);
        jsonBody.put("Description", Discription);
        jsonBody.put("Mortgage", Mortgage);

        AddRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {

            boolean res;
            progress.dismiss();

            try {
              res = response.getBoolean("Status");
            } catch (JSONException e) {
              res = false;
            }

            if (res) {

              //در اینجا اگر کاربر از صفحه املاک من دکمه ویرایش را فشار داده باشد و وارد این صفحه شده باشد آن آیتم در sqlite حذف خواهد شد
              if (DA_Add_Home.ItemId != 0) {
                Cursor Remove_ItemFeatures = dbAdapter.ExecuteQ("delete from TblItemFeatuers where FkItem=" + DA_Add_Home.ItemId);
                Cursor Remove_Images = dbAdapter.ExecuteQ("delete from TblItemImages where FkItem=" + DA_Add_Home.ItemId);
                Cursor Remove_Item = dbAdapter.ExecuteQ("delete from TblItem where Id=" + DA_Add_Home.ItemId);
                Remove_Images.close();
                Remove_Item.close();
                Remove_ItemFeatures.close();
                dbAdapter.close();
                btn_Done.setEnabled(true);
              }

              Toast.makeText(getActivity(), "عملیات با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
              DA_Add_Home.ClearDA_AddHome();

              Stack_Back.MyStack_Back.Pop(getActivity());

            } else {
              try {
                new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml(response.getString("Titel")))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
              } catch (JSONException e) {
                e.printStackTrace();
              }
              btn_Done.setEnabled(true);
            }
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            progress.dismiss();
            new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>خطای در عملیات رخ داده است لطفا بعدا امتحان کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
            btn_Done.setEnabled(true);
          }
        });

        AppController.getInstance().addToRequestQueue(Policy_Volley.SetPostRetryAndPolicy(AddRequest));

      } catch (JSONException e) {
        progress.dismiss();
        new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>خطای سمت سرور لطفا بعدا امتحان کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
        btn_Done.setEnabled(true);
      }
    } else {
      progress.dismiss();
      new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا اول یک حساب کاربری جدید ایجاد کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
      btn_Done.setEnabled(true);
    }

  }

  //دراین جا اطلاعات در sqlite برنامه ذخیره می شود این تابع با فشار دادن دکمه برای بعد فراخوانی می شود
  public void LaterSave() {

    AfterToSave.setEnabled(false);

    if (CheckValid()) {
      if (rdo_Commercial.isChecked() || rdo_Industrial.isChecked() || rdo_Residential.isChecked()) {
        //در اینجا progressbar لودینگ نمایش داده می شود
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle((Html.fromHtml("<font color='#FF7F27'>در حال ارسال</font>")));
        progress.setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا منتظر بمانید</font>")));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        int FkUser = new User(getActivity()).GetUserId();

        if (FkUser != 0) {

          //در اینجا متغیر های لازم برای اطلاعات ایجا شده است

          final String Price;
          String Mortgage;

          String Title = txt_AdTitle.getText().toString();

          //متراژ
          String Area = txt_TheArea.getText().toString();
          String CellPhone = txt_Phone.getText().toString();
          String Tel = txt_Mobile.getText().toString();
          String VideoUrl = txt_VideoLink.getText().toString();
          String Discription = txt_Discription.getText().toString();

          //تعداد اتاق
          int Room;
          if (DA_Add_Home.CountRoom == 0)
            Room = 0;
          else
            Room = Integer.parseInt(cmb_CountRoom.getItemAtPosition(DA_Add_Home.CountRoom).toString());

          String Age;
          if (DA_Add_Home.cmb_Year_of_construction != -1)
            Age = cmb_AgeHome.getItemAtPosition(DA_Add_Home.cmb_Year_of_construction).toString();
          else
            Age = "0";

          String Code = txt_CodePosty.getText().toString();

          String Address = txt_Address.getText().toString();

          //برای فروش و اجاره فروش:1 و اجاره:0
          int enumTarget;

          if (DA_Add_Home.rdo_Rent == true) {
            enumTarget = 0;
            if (txt_Rent.getText().toString().equalsIgnoreCase(""))
              Price = "0";
            else
              Price = txt_Rent.getText().toString();

            if (txt_Mortgage.getText().toString().equalsIgnoreCase(""))
              Mortgage = "0";
            else
              Mortgage = txt_Mortgage.getText().toString();
          } else {
            enumTarget = 1;
            if (txt_Price.getText().toString().equalsIgnoreCase(""))
              Price = "0";
            else
              Price = txt_Price.getText().toString();

            Mortgage = "0";
          }


          //برای وضعیت آیتم در حال انتظار:0 رد شده:1 تایید شده:2
          int enumKind = 0;

          int FkType = DA_Add_Home.TypeHome;

          int FkLocation = locationId;


          List<VM_ItemFeature> itemFeatures = new ArrayList<>();


          //در اینجا ویژگی های منزل set می شوند
          if (DA_Add_Home.booleans != null) {
            for (int i = 0; i < DA_Add_Home.booleans.size(); i++) {
              String Val = DA_Add_Home.booleans.get(i).getVal().toString();
              int Id = DA_Add_Home.booleans.get(i).getId();
              itemFeatures.add(new VM_ItemFeature(Val, Id, 2));
            }
          }

          if (DA_Add_Home.selects != null) {
            for (int i = 0; i < DA_Add_Home.selects.size(); i++) {
              String Val = DA_Add_Home.selects.get(i).getVal().toString();
              int Id = DA_Add_Home.selects.get(i).getId();
              itemFeatures.add(new VM_ItemFeature(Val, Id, 4));
            }
          }

          if (DA_Add_Home.numbers != null) {
            for (int i = 0; i < DA_Add_Home.numbers.size(); i++) {
              String Val = String.valueOf(DA_Add_Home.numbers.get(i).getVal());
              int Id = DA_Add_Home.numbers.get(i).getId();
              itemFeatures.add(new VM_ItemFeature(Val, Id, 3));
            }

          }

          if (DA_Add_Home.texts != null) {
            for (int i = 0; i < DA_Add_Home.texts.size(); i++) {
              String Val = String.valueOf(DA_Add_Home.texts.get(i).getVal());
              int Id = DA_Add_Home.texts.get(i).getId();
              itemFeatures.add(new VM_ItemFeature(Val, Id, 1));
            }
          }

          List<String> Images = new ArrayList<>();
          //در اینجا عکس ها در کارت حافظه ذخیره می شوند

          for (int i = 0; i < img_homes.GetValues().size(); i++) {
            String Name = String.valueOf(GetRandom.GetLong()) + ".jpg";

            if (SaveImageToMob.SaveImageToSdCard(img_homes.GetValues().get(i), Name))
              Images.add(Name);
          }

          //در اینجا در صفحه املاک من دکمه ویرایش را فشار می دهد در اینجا آیتم مورد نظر کاربر حذف می شود
          if (DA_Add_Home.ItemId != 0) {
            Cursor Remove_ItemFeatures = dbAdapter.ExecuteQ("delete from TblItemFeatuers where FkItem=" + DA_Add_Home.ItemId);
            Cursor Remove_Images = dbAdapter.ExecuteQ("delete from TblItemImages where FkItem=" + DA_Add_Home.ItemId);
            Cursor Remove_Item = dbAdapter.ExecuteQ("delete from TblItem where Id=" + DA_Add_Home.ItemId);
            Remove_Images.close();
            Remove_Item.close();
            Remove_ItemFeatures.close();
            dbAdapter.close();
          }

          //در اینجا بزرگترین id رو به دست می آوریم
          Cursor GetIdItem = dbAdapter.ExecuteQ("select MAX(Id) from TblItem");

          int IdItem;
          if (GetIdItem.getString(0) != null)
            IdItem = Integer.parseInt(GetIdItem.getString(0)) + 1;
          else
            IdItem = 1;

          Cursor AddItem = dbAdapter.ExecuteQ("insert into TblItem('Id','Title','Price','Area','CellPhone','Tel','VideoUrl','Room','Age','Code','Address','enumTarget','enumKind','FkType','FkLocation','FkUser','FkUserPro','MapLocation','Description','Mortgage') values (" + IdItem + ",'" + Title + "','" + Price + "'," + Area + ",'" + CellPhone + "','" + Tel + "','" + VideoUrl + "'," + Room + "," + Age + ",'" + Code + "','" + Address + "'," + enumTarget + "," + enumKind + "," + FkType + "," + FkLocation + "," + FkUser + ",null,null,'" + Discription + "','" + Mortgage + "')");
          AddItem.close();

          //در اینجا آدرس عکس هارا در دیتابیس ذخیره می کنیم
          //در اینجا بزرگترین id رو به دست می آوریم

          Cursor GetIdImage = dbAdapter.ExecuteQ("select Max(Id) from TblItemImages");
          int IdImage;

          if (GetIdImage.getString(0) != null)
            IdImage = Integer.parseInt(GetIdImage.getString(0)) + 1;
          else
            IdImage = 1;

          for (int i = 0; i < Images.size(); i++) {

            Cursor AddImage = dbAdapter.ExecuteQ("insert into TblItemImages ('Id','ImageUrl','FkItem') values (" + IdImage + ",'" + Images.get(i) + "'," + IdItem + ")");
            AddImage.close();
            IdImage++;
          }

          //در اینجا ویژگی های منزل ثبت می شوند

          //در اینجا بزرگترین id رو به دست می آوریم

          Cursor GetIdFeature = dbAdapter.ExecuteQ("select Max(Id) from TblItemFeatuers");
          int IdFeature;
          if (GetIdFeature.getString(0) != null)
            IdFeature = Integer.parseInt(GetIdFeature.getString(0)) + 1;
          else
            IdFeature = 1;

          for (int i = 0; i < itemFeatures.size(); i++) {

            Cursor AddFeature = dbAdapter.ExecuteQ("insert into TblItemFeatuers ('Id','FkItem','FkFeatures','FkFormat','Titel','Value') values (" + IdFeature + "," + IdItem + "," + itemFeatures.get(i).getFkFeatures() + "," + itemFeatures.get(i).getFkFormat() + ",'','" + itemFeatures.get(i).getValue() + "')");
            AddFeature.close();
            IdFeature++;
          }

          progress.dismiss();
          DA_Add_Home.ClearDA_AddHome();
          Toast.makeText(getActivity(), "عملیات با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
          Stack_Back.MyStack_Back.Pop(getActivity());

        } else {
          progress.dismiss();
          new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا اول یک حساب کاربری جدید ایجاد کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
        }
        AfterToSave.setEnabled(true);
      } else {
        new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا ویژگی های منزل خود را وارد نمایید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
      }
    } else {
      AfterToSave.setEnabled(true);
      Toast.makeText(getContext(), "لطفا اطلاعات را کامل وارد نمایید", Toast.LENGTH_SHORT).show();
    }
  }


  //این متد برای زمانی که کاربر می خواهد منزل خودر ویرایش کند عکس های رو که قبلا ذخیره کرده در recycler مربوط به عکس ها نمایش داده می شود
  void SetImage() {
    Cursor ImageList = dbAdapter.ExecuteQ("select * from TblItemImages where FkItem=" + DA_Add_Home.ItemId);
    if (ImageList != null && ImageList.moveToFirst()) {
      while (!ImageList.isAfterLast()) {
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File f = new File(sdCardDirectory, "Shaar/" + ImageList.getString(1));
        if (f.exists()) {
          Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());

          if (DA_Add_Home.Images == null) {
            DA_Add_Home.Images = new ArrayList<>();
          }

          DA_Add_Home.Images.add(f.getPath());

          img_homes.Add(bmp);
          SetImageHomes();
        }
        ImageList.moveToNext();
      }
    }
  }

  //در اینجا چک می شود که تکست باکس ها valid باشند
  boolean CheckValid() {
    boolean valid = true;

    if (validation.Required(txt_AdTitle, getResources().getString(ir.tdaapp.diako.shaar.R.string.ThisFieldNotEmpty))) {
      valid = false;
    }

    if (validation.Required(txt_Discription, getResources().getString(ir.tdaapp.diako.shaar.R.string.ThisFieldNotEmpty))) {
      valid = false;
    }

    if (validation.Required(txt_Address, getResources().getString(ir.tdaapp.diako.shaar.R.string.ThisFieldNotEmpty))) {
      valid = false;
    }

    if (validation.Required(txt_Mobile, getResources().getString(ir.tdaapp.diako.shaar.R.string.ThisFieldNotEmpty))) {
      valid = false;
    }

    if (validation.Required(txt_TheArea, getResources().getString(ir.tdaapp.diako.shaar.R.string.ThisFieldNotEmpty))) {
      valid = false;
    }

    if (rdo_Sell.isChecked()) {
      if (validation.Required(txt_Price, getResources().getString(ir.tdaapp.diako.shaar.R.string.ThisFieldNotEmpty))) {
        valid = false;
      }
    }

    if (rdo_Rent.isChecked()) {
      if (validation.Required(txt_Mortgage, getResources().getString(ir.tdaapp.diako.shaar.R.string.ThisFieldNotEmpty))) {
        valid = false;
      }

      if (validation.Required(txt_Rent, getResources().getString(ir.tdaapp.diako.shaar.R.string.ThisFieldNotEmpty))) {
        valid = false;
      }
    }
    if (!valid)
      return valid;

    if (validation.StringLength(txt_AdTitle, 3, 100, "تعداد کاراکتر باید بین 3 تا 100 کاراکتر باشد")) {
      valid = false;
    }

    if (validation.StringLength(txt_CodePosty, 3, 50, "تعداد کاراکتر باید بین 3 تا 50 کاراکتر باشد")) {
      valid = false;
    }

    BigInteger MaxPrice = new BigInteger("999000000000");
    BigInteger MinPrice = new BigInteger("1000");

    if (rdo_Sell.isChecked()) {
      if (validation.PriceValid(txt_Price, MinPrice, MaxPrice, "قیمت باید بین 1000 تا 99000000000")) {
        valid = false;
      }
    }

    if (rdo_Rent.isChecked()) {
      if (validation.PriceValid(txt_Rent, MinPrice, MaxPrice, "قیمت باید بین 1000 تا 99000000000")) {
        valid = false;
      }

      if (validation.PriceValid(txt_Mortgage, MinPrice, MaxPrice, "قیمت باید بین 1000 تا 99000000000")) {
        valid = false;
      }
    }

    if (validation.MinValue(txt_TheArea, "متراژ باید حداقل یک متر باشد", 1)) {
      valid = false;
    }

    if (validation.ValidMobile(txt_Mobile, "شماره موبایل صحیح نمی باشد")) {
      valid = false;
    }

    if (validation.MaxLength(txt_Phone, "شماره تلفن صحیح نیست", 15)) {
      valid = false;
    }

    if (validation.MinLength(txt_Discription, "تعداد کاراکتر باید حداقل 3 کاراکنر باشد", 3)) {
      valid = false;
    }

    return valid;
  }

  //در اینجا عکس ها رادر سرور ذخیره می کند و نام آن ها را برگشت می دهد
  void SaveImageAndDataToServer() {

    if (CheckValid()) {
      if (rdo_Commercial.isChecked() || rdo_Industrial.isChecked() || rdo_Residential.isChecked()) {

        if (DA_Add_Home.Images != null) {

          if (DA_Add_Home.Images.size() > 0) {
            btn_Done.setEnabled(false);

            //در اینجا progressbar لودینگ نمایش داده می شود
            final ProgressDialog progress = new ProgressDialog(getActivity());
            progress.setTitle((Html.fromHtml("<font color='#FF7F27'>در حال ارسال</font>")));
            progress.setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا منتظر بمانید</font>")));
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();

            new Thread(() -> {

              String Url = Api + "User/PostFile";
              _FileManger = new FileManger(Url);

              List<String> vals = new ArrayList<>();

              for (String i : DA_Add_Home.Images) {
                Log.i("LOG", "content: " + i);
                vals.add(_FileManger.uploadFile(i).replace("\"", ""));
              }

              SaveData(vals, progress);
            }).start();

          } else {
            btn_Done.setEnabled(true);
            Toast.makeText(getContext(), getResources().getString(R.string.PleaseAddedImageYourHome), Toast.LENGTH_SHORT).show();
          }

        } else {
          btn_Done.setEnabled(true);
          Toast.makeText(getContext(), getResources().getString(R.string.PleaseAddedImageYourHome), Toast.LENGTH_SHORT).show();
        }

      } else {
        btn_Done.setEnabled(true);
        new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا ویژگی های منزل خود را وارد نمایید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
      }
    } else {
      btn_Done.setEnabled(true);
      Toast.makeText(getContext(), "لطفا اطلاعات را کامل وارد نمایید", Toast.LENGTH_SHORT).show();
    }
  }
}