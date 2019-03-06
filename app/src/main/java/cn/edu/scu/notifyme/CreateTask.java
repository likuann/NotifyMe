package cn.edu.scu.notifyme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.scu.notifyme.event.EventID;
import cn.edu.scu.notifyme.event.MessageEvent;
import cn.edu.scu.notifyme.model.Message;
import cn.edu.scu.notifyme.model.Rule;

public class CreateTask extends AppCompatActivity {

    private AlertDialog testprogress;
    private AlertDialog testresult;
    private AlertDialog testtimeout;

    @BindView(R.id.task_name)
    EditText inputtaskName;
    @BindView(R.id.toload_url)
    EditText inputtoloadurl;
    @BindView(R.id.duration)
    EditText inputduration;
    @BindView(R.id.code)
    EditText inputcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        BackgroundWorker.getInstance().bind(this);
        BackgroundWorker.getInstance().start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        switch (event.getId()) {
            case EventID.EVENT_HAS_FETCHED_RESULT:
                if (testprogress.isShowing()) {
                    testprogress.dismiss();
                    showresult(event.getMessage());
                }
                break;
            case EventID.EVENT_FETCH_TIMEOUT:
                if (testprogress.isShowing()) {
                    testprogress.dismiss();
                    timeout();
                }
                break;
        }
    }

    @OnClick({R.id.test, R.id.cancel, R.id.create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.test:
                test();
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.create:
                create();
                break;
        }
    }

    private void test() {

        if (inputcode.getText().length() < 1 || inputtoloadurl.getText().length() < 1) {
            ToastUtils.showShort("URL或者代码为空");
            return;
        }

        Rule testrule = new Rule();
        testrule.setName(String.valueOf(inputtaskName.getText()));
        testrule.setScript(String.valueOf(inputcode.getText()));
        testrule.setToLoadUrl(String.valueOf(inputtoloadurl.getText()));
        BackgroundWorker.getInstance().insertTask(testrule);

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateTask.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.test_progress, null);
        TextView tv_cancel = v.findViewById(R.id.cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testprogress.dismiss();
            }
        });
        builder.setView(v);
        testprogress = builder.create();
        testprogress.setCancelable(true);
        testprogress.setCanceledOnTouchOutside(false);
        testprogress.show();
    }

    private void showresult(Message msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateTask.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.test_result, null);
        TextView tv_title = v.findViewById(R.id.result_title);
        TextView tv_message = v.findViewById(R.id.result_message);
        ImageView iv_image = v.findViewById(R.id.result_image);
        TextView tv_confirm = v.findViewById(R.id.result_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testresult.dismiss();
            }
        });
        if (msg.getTitle() != null)
            tv_title.setText(msg.getTitle());
        if (msg.getContent() != null)
            tv_message.setText(msg.getContent());
        if (msg.getImgUrl() != null)
            Glide.with(this).load(msg.getImgUrl()).into(iv_image);
        builder.setView(v);
        testresult = builder.create();
        testresult.setCancelable(true);
        testresult.setCanceledOnTouchOutside(false);
        testresult.show();
    }

    private void timeout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateTask.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.test_timeout, null);
        TextView tv_confirm = v.findViewById(R.id.confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testtimeout.dismiss();
            }
        });
        builder.setView(v);
        testtimeout = builder.create();
        testtimeout.setCancelable(true);
        testtimeout.setCanceledOnTouchOutside(false);
        testtimeout.show();
    }

    private void create() {
        if (inputcode.getText().length() < 1 ||
                inputtoloadurl.getText().length() < 1 ||
                inputduration.getText().length() < 1 ||
                inputtaskName.length() < 1) {
            ToastUtils.showShort("不能为空");
            return;
        }
    }
}