package com.zyf.bings.bingos.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyifei on 17/9/7.
 */

public class TableView extends View {

    /**
     * 单元格基准宽度，设权重的情况下，为最小单元格宽度
     */
    private float unitColumnWidth;
    private float rowHeight;
    private float dividerWidth;
    private int dividerColor;
    private float textSize;
    private int textColor;

    private int rowCount;
    private int columnCount;

    private Paint paint;

    private float[] columnLefts;
    private float[] columnWidths;

    private int[] columnWeights;
    private List<String[]> tableContents;

    private Context mContext;

    public TableView(Context context) {
        super(context);
        init(null);
        this.mContext = context;
    }

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        this.mContext = context;
    }

    private void init(AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        tableContents = new ArrayList<>();
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TableView);
            unitColumnWidth = typedArray.getDimensionPixelSize(R.styleable.TableView_unitColumnWidth, 0);
            rowHeight = typedArray.getDimensionPixelSize(R.styleable.TableView_rowHeight, DensityUtil.dp2px(getContext(), 36));
            dividerWidth = typedArray.getDimensionPixelSize(R.styleable.TableView_dividerWidth, 1);
            dividerColor = typedArray.getColor(R.styleable.TableView_dividerColor, Color.parseColor("#E1E1E1"));
            textSize = typedArray.getDimensionPixelSize(R.styleable.TableView_textSize, DensityUtil.dp2px(getContext(), 10));
            textColor = typedArray.getColor(R.styleable.TableView_textColor, Color.parseColor("#999999"));
            typedArray.recycle();
        } else {
            unitColumnWidth = 0;
            rowHeight = DensityUtil.dp2px(getContext(), 36);
            dividerWidth = 1;
            dividerColor = Color.parseColor("#E1E1E1");
            textSize = DensityUtil.dp2px(getContext(), 10);
            textColor = Color.parseColor("#999999");
        }
        initTableSize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //通过权重计算最小单元格宽度
        int weightSum = 0;
        if (columnWeights != null) {
            for (int i = 0; i < columnCount; i++) {
                if (columnWeights.length > i) {
                    weightSum += columnWeights[i];
                } else {
                    weightSum += 1;
                }
            }
        } else {
            //默认等分，每列权重为1
            weightSum = columnCount;
        }

        //计算宽度及列宽
        float width;
        if (unitColumnWidth == 0) {
            //未设置宽度，根据控件宽度来确定最小单元格宽度
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            width = getMeasuredWidth();
            unitColumnWidth = (width - (columnCount + 1) * dividerWidth) / weightSum;
        } else {
            //设置了最小单元格宽度
            width = dividerWidth * (columnCount + 1) + unitColumnWidth * weightSum;
        }
        //计算高度
        float height = (dividerWidth + rowHeight) * rowCount + dividerWidth;

        setMeasuredDimension((int) width, (int) height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateColumns();
        // drawHeader(canvas);
        drawFramework(canvas);
        drawContent(canvas);
    }


    /**
     * 画整体表格框架
     */
    private void drawFramework(Canvas canvas) {
        paint.setColor(dividerColor);
        for (int i = 0; i < columnCount + 1; i++) {
            if (i == 0) {
                //最左侧分割线
                //canvas.drawRect(0, 0, dividerWidth, getHeight(), paint);
                continue;
            }
            if (i == columnCount) {
                //最右侧分割线
                //canvas.drawRect(getWidth() - dividerWidth, 0, getWidth(), getHeight(), paint);
                continue;
            }
            canvas.drawRect(columnLefts[i], 0, columnLefts[i] + dividerWidth, getHeight(), paint);
        }
        for (int i = 0; i < rowCount + 1; i++) {


            if (i == 0) {

            } else {
                canvas.drawRect(0, i * (rowHeight + dividerWidth), getWidth(), i * (rowHeight + dividerWidth) + dividerWidth, paint);
            }
        }
    }

    /**
     * 画内容
     */
    private void drawContent(Canvas canvas) {
        for (int i = 0; i < rowCount; i++) {
            final String[] rowContent = tableContents.size() > i ? tableContents.get(i) : new String[0];
            paint.setColor(textColor);
            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.LEFT);
            for (int j = 0; j < columnCount; j++) {
                if (rowContent.length > j) {
                    canvas.drawText(rowContent[j],
                            columnLefts[j] + columnWidths[j] / 10,
                            getTextBaseLine(i * (rowHeight + dividerWidth), paint),
                            paint);
                }
            }
        }
    }

    /**
     * 计算每列左端坐标及列宽
     */
    private void calculateColumns() {
        columnLefts = new float[columnCount];
        columnWidths = new float[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnLefts[i] = getColumnLeft(i);
            columnWidths[i] = getColumnWidth(i);
        }
    }

    private float getColumnLeft(int columnIndex) {
        if (columnWeights == null) {
            return columnIndex * (unitColumnWidth + dividerWidth);
        }
        //计算左边的权重和
        int weightSum = 0;
        for (int i = 0; i < columnIndex; i++) {
            if (columnWeights.length > i) {
                weightSum += columnWeights[i];
            } else {
                weightSum += 1;
            }
        }
        return columnIndex * dividerWidth + weightSum * unitColumnWidth;
    }

    private float getColumnWidth(int columnIndex) {
        if (columnWeights == null) {
            return unitColumnWidth;
        }
        int weight = columnWeights.length > columnIndex ? columnWeights[columnIndex] : 1;
        return weight * unitColumnWidth;
    }

    private float getTextBaseLine(float rowStart, Paint paint) {
        final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (rowStart + (rowStart + rowHeight) - fontMetrics.bottom - fontMetrics.top) / 2;
    }

    /**
     * 设置表格内容
     */
    public TableView clearTableContents() {
        columnWeights = null;
        tableContents.clear();
        return this;
    }

    /**
     * 设置每列的权重
     *
     * @param columnWeights
     * @return
     */
    public TableView setColumnWeights(int... columnWeights) {
        this.columnWeights = columnWeights;
        return this;
    }


    /**
     * 设置表格内容
     */
    public TableView addContent(String... contents) {
        tableContents.add(contents);
        return this;
    }

    /**
     * 设置表格内容
     */
    public TableView addContents(List<String[]> contents) {
        tableContents.addAll(contents);
        return this;
    }

    /**
     * 初始化行列数
     */
    private void initTableSize() {
        rowCount = tableContents.size();
        if (rowCount > 0) {
            //如果设置了表头，根据表头数量确定列数
            columnCount = tableContents.get(0).length;

            if (tableContents.get(0).length == 1) {
                columnCount = 2;
            }
        }
    }

    /**
     * 设置数据后刷新表格
     */
    public void refreshTable() {
        initTableSize();
        invalidate();
    }

}