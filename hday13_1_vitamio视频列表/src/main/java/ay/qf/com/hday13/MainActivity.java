package ay.qf.com.hday13;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * 在页面中显示一个列表，列表的每个item中显示一个文本，一个图片，当图片被点击时，
 * 播放视频
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRcv;
    private ArrayList<String> list = new ArrayList<>();
    private String[] str = {"http://bvideo.spriteapp.cn/video/2016/0610/575add617fc8b_wpd.mp4 ",
                            "http://wvideo.spriteapp.cn/video/2016/0610/cdbba1a0-2ef5-11e6-ac8f-90b11c479401_wpd.mp4 "};
    private MyAdapter adapter;

    private VideoView lastVV; //用于记录上一个正在播放视频的VideoView对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();

        initData();
        
        initAdapter();
    }

    private void initAdapter() {
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        mRcv.setAdapter(adapter);

    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            list.add(str[i%str.length]);
        }
    }

    private void initView() {
        mRcv = (RecyclerView) findViewById(R.id.rcv);
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item,parent,false));
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, final int position) {
            holder.tv.setText(position+"  网址 ：  "+list.get(position));
            holder.iv.setImageResource(R.mipmap.ic_launcher);

            //为防止在列表上下滑动期间，由于重用引起的VideoView在错误的位置播放的bug
            holder.vv.setVisibility(View.GONE);
            if (holder.vv.isPlaying()) {
                holder.vv.stopPlayback();
            }

            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("tmd", "onClick: "+(lastVV != null && lastVV.isPlaying()));
                    //停止上一个正在播放的视频
                    if (lastVV != null ) {
                        lastVV.stopPlayback();
                    }
                    //播放视频
                    holder.vv.setVisibility(View.VISIBLE);
                    holder.vv.setVideoPath(list.get(position));
                    holder.vv.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE,2);

                    lastVV = holder.vv;
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView tv;
            ImageView iv;
            VideoView vv;

            public MyHolder(View itemView) {
                super(itemView);

                tv = (TextView)itemView.findViewById(R.id.textView);
                iv = (ImageView)itemView.findViewById(R.id.imageView);
                vv = (VideoView)itemView.findViewById(R.id.vv);

            }
        }
    }
}
