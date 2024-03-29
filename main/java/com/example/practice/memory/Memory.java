package com.example.practice.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.practice.R;

public class Memory extends Activity {

    private Chronometer			chronometer;
    private static int			rowCount	= 8;
    private static int			columeCount	= 4;
    private static int			items;
    private Context				context;
    private Drawable			backImage;
    private int[][]				cards;
    private List<Drawable>		images;
    private Card				firstCard;
    private Card				seconedCard;
    private ButtonListener		buttonListener;
    private static Object		lock		= new Object();
    int							pairCount;
    private TableLayout			mainTable;
    private UpdateCardsHandler	handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new UpdateCardsHandler();
        loadImages();
        setContentView(R.layout.activity_memory);

        backImage = getResources().getDrawable(R.drawable.empty);
        buttonListener = new ButtonListener();
        mainTable = (TableLayout) findViewById(R.id.MyTableLayout);
        context = mainTable.getContext();

        chronometer = (Chronometer) findViewById(R.id.MyChronometer);
        chronometer.setFormat("遊戲時間： %s");

        initilizeGame();
    }

    private void initilizeGame() {
        cards = new int[columeCount][rowCount];
        items = (rowCount * columeCount) / 2; // 記錄可配對個數

        mainTable.removeAllViews();

        for (int y = 0; y < rowCount; y++) {
            mainTable.addView(createRow(y));
        }

        firstCard = null;
        loadCards(); // 產生卡片
        pairCount = 0;

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void loadImages() {
        images = new ArrayList<Drawable>();

        images.add(getResources().getDrawable(R.drawable.item01));
        images.add(getResources().getDrawable(R.drawable.item02));
        images.add(getResources().getDrawable(R.drawable.item03));
        images.add(getResources().getDrawable(R.drawable.item04));
        images.add(getResources().getDrawable(R.drawable.item05));
        images.add(getResources().getDrawable(R.drawable.item06));
        images.add(getResources().getDrawable(R.drawable.item07));
        images.add(getResources().getDrawable(R.drawable.item08));
        images.add(getResources().getDrawable(R.drawable.item09));
        images.add(getResources().getDrawable(R.drawable.item10));
        images.add(getResources().getDrawable(R.drawable.item11));
        images.add(getResources().getDrawable(R.drawable.item12));
        images.add(getResources().getDrawable(R.drawable.item13));
        images.add(getResources().getDrawable(R.drawable.item14));
        images.add(getResources().getDrawable(R.drawable.item15));
        images.add(getResources().getDrawable(R.drawable.item16));
    }

    private void loadCards() {
        try {

            int size = rowCount * columeCount;
            ArrayList<Integer> list = new ArrayList<Integer>();

            for (int i = 0; i < size; i++) {
                list.add(new Integer(i)); // 加入所有卡片編號
            }

            Random r = new Random();

            for (int i = size - 1; i >= 0; i--) {
                int t = 0;

                if (i > 0) {
                    t = r.nextInt(i); // 隨機取得編號
                }

                t = list.remove(t).intValue(); // 從 list 中取出編號
                cards[i % columeCount][i / columeCount] = t % (size / 2); // 將編號放入指定位置
            }

            // 再次洗牌
            for (int i = 0; i < rowCount; i++)
                for (int j = 0; j < columeCount; j++) {
                    int rc = r.nextInt(rowCount);
                    int cc = r.nextInt(columeCount);
                    int temp;

                    temp = cards[i][j];
                    cards[i][j] = cards[rc][cc];
                    cards[rc][cc] = temp;
                }
        }
        catch (Exception e) {
        }

    }

    private TableRow createRow(int y) {
        TableRow row = new TableRow(context);
        row.setHorizontalGravity(Gravity.CENTER);

        for (int x = 0; x < columeCount; x++) {
            row.addView(createImageButton(x, y));
        }
        return row;
    }

    private View createImageButton(int x, int y) {
        Button button = new Button(context);
        button.setBackgroundDrawable(backImage);
        button.setId(100 * x + y);
        button.setOnClickListener(buttonListener);
        return button;
    }

    class ButtonListener implements OnClickListener {
        public void onClick(View v) {
            synchronized (lock) {
                if (firstCard != null && seconedCard != null) {
                    return;
                }
                int id = v.getId();
                int x = id / 100;
                int y = id % 100;
                turnCard((Button) v, x, y);
            }
        }

        private void turnCard(Button button, int x, int y) {
            button.setBackgroundDrawable(images.get(cards[x][y]));

            if (firstCard == null) {
                firstCard = new Card(button, x, y);
            }
            else {
                if (firstCard.x == x && firstCard.y == y) {
                    return; // 選到相同的卡片則不動作
                }

                seconedCard = new Card(button, x, y);

                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            synchronized (lock) {
                                handler.sendEmptyMessage(0);
                            }
                        }
                        catch (Exception e) {
                        }
                    }
                };

                Timer t = new Timer(false);
                t.schedule(tt, 500); // 停頓0.5秒
            }
        }
    }

    class UpdateCardsHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            synchronized (lock) {
                checkCards();
            }
        }

        public void checkCards() {
            if (cards[seconedCard.x][seconedCard.y] == cards[firstCard.x][firstCard.y]) {
                firstCard.button.setEnabled(false);
                seconedCard.button.setEnabled(false);
                Toast.makeText(getApplicationContext(), "配對成功！", Toast.LENGTH_SHORT).show();
                pairCount++;
                if (pairCount >= items) {
                    chronometer.stop();

                    final String[] list = {"重新遊戲","關閉程式"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Memory.this);
                    dialog.setTitle("恭喜你完成所有配對！");
                    dialog.setItems(list,new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Toast.makeText(Memory.this, list[which], Toast.LENGTH_SHORT).show();
                            switch (list[which]){
                                case "重新遊戲": // 重新遊戲
                                    initilizeGame();
                                    break;

                                case "關閉程式": // 關閉程式
                                    Memory.this.finish();
                            }
                        }
                    });
                    dialog.show();

                }
            }
            else {
                seconedCard.button.setBackgroundDrawable(backImage);
                firstCard.button.setBackgroundDrawable(backImage);
                Toast.makeText(getApplicationContext(), "配對錯誤...", Toast.LENGTH_SHORT).show();
            }
            firstCard = null;
            seconedCard = null;
        }
    }
}

