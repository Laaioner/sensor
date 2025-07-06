package com.example.laucher;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

     ListView listView;
     PackageManager packageManager;
     List<ApplicationInfo> packageInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview_apps);
        packageManager = getPackageManager();
        packageInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);

        AppAdapter appAdapter = new AppAdapter(this, R.layout.item_lista, packageInfoList);
        listView.setAdapter(appAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationInfo applicationInfo = (ApplicationInfo) parent.getItemAtPosition(position);
                String packageName = applicationInfo.packageName;

                Intent intent = packageManager.getLaunchIntentForPackage(packageName);
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Não foi possível abrir o aplicativo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
