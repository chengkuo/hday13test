package ay.qf.com.hday13_2_windowmanager;

import android.Manifest;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButAdd;
    private Button mButRemove;

    private WindowManager manager;
    private static ImageView iv;
    private static boolean flag = true;

    private float dx,dy;  //记录手指与左上角的x,y间距

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

        //初始化WindowManager对象
        manager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    }

    private void initView() {
        mButAdd = (Button) findViewById(R.id.but_add);
        mButRemove = (Button) findViewById(R.id.but_remove);

        mButAdd.setOnClickListener(this);
        mButRemove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_add:
                if (flag) {
                    flag = false;
                    //向屏幕中添加一个机器人的图片
                    iv = new ImageView(this);
                    iv.setImageResource(R.drawable.pic3);

                    //设置iv在添加到屏幕时的布局参数特点
                    final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                    params.type = WindowManager.LayoutParams.TYPE_PHONE;
                    //设置显示宽高
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置整体透明度
                    params.alpha = 120;
                    //设置背景透明效果
                    params.format = PixelFormat.RGBA_8888;

                    //设置当前窗口不获取焦点
                    params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

                    //调整显示位置
//                    params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;


                    //将此iv控件添加到屏幕中去
                    manager.addView(iv, params);

                    //给iv添加触摸事件
                    iv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Log.i("tmd", "onTouch: " + event.getAction());
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    dx = event.getRawX()-params.x;
                                    dy = event.getRawY() -params.y;

                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    //左上角点的位置
                                    params.x = (int) (event.getRawX() - dx);
                                    params.y = (int) (event.getRawY()-dy);
//                                    params.gravity = Gravity.NO_GRAVITY;
                                    //更新iv的布局参数
                                    manager.updateViewLayout(iv,params);
                                    break;
                            }

                            return true;
                        }
                    });
                }


                break;
            case R.id.but_remove:
                if (!flag) {
                    flag = true;
                    manager.removeView(iv);
                }


                break;
        }
    }
}
