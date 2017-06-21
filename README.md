# SolarexIoCDemo
自制运行时Ioc框架

[ ![Download](https://api.bintray.com/packages/solarexsoft/maven/SolarexIoC/images/download.svg) ](https://bintray.com/solarexsoft/maven/SolarexIoC/_latestVersion)

``compile 'com.solarexsoft.solarexioc:solarexioc:1.0.0'``

Usage:
```
@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Solarex";
    @ViewInject(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
        Log.d(TAG, "onCreate: tv = " + tv);
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void clickTest(View view) {
        int id = view.getId();
        if (id == R.id.btn1) {
            Log.d(TAG, "clickTest: btn1 clicked");
        } else if (id == R.id.btn2) {
            Log.d(TAG, "clickTest: btn2 clicked");
        }
    }
}
```
