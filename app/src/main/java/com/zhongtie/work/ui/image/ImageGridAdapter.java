package com.zhongtie.work.ui.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.zhongtie.work.R;
import com.zhongtie.work.app.Constant;
import com.zhongtie.work.event.EventData;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.util.image.ImageLoader;
import com.zhongtie.work.util.image.ImageLoaderUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片Adapter
 */
public class ImageGridAdapter extends BaseAdapter {
    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_NORMAL = 1;
    private Context mContext;
    private LayoutInflater mInflater;
    private boolean showCamera = true;
    private boolean showSelectIndicator = true;
    private List<Image> mImages = new ArrayList<>();
    private List<String> mSelectedImages = new ArrayList<>();
    private RelativeLayout.LayoutParams params;

    public ImageGridAdapter(Context context, boolean showCamera, int column) {
        params = new RelativeLayout.LayoutParams(ViewUtils.getScreenWidth(context) / 3, ViewUtils.getScreenWidth(context) / 3);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showCamera = showCamera;
    }

    /**
     * 显示选择指示器
     *
     * @param b
     */
    public void showSelectIndicator(boolean b) {
        showSelectIndicator = b;
    }

    public void setShowCamera(boolean b) {
        if (showCamera == b) return;
        showCamera = b;
        notifyDataSetChanged();
    }

    public boolean isShowCamera() {
        return showCamera;
    }

    /**
     * 选择某个图片，改变选择状态
     *
     * @param image
     */
    public void select(Image image) {
        if (mSelectedImages.contains(image.path)) {
            mSelectedImages.remove(image.path);
        } else {
            mSelectedImages.add(image.path);
        }
        notifyDataSetChanged();
    }

    /**
     * 通过图片路径设置默认选择
     *
     * @param resultList 默认选择列表
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        for (String path : resultList) {
            Image image = getImageByPath(path);
            if (image != null) {
                mSelectedImages.add(path);
            }
        }
        if (mSelectedImages.size() > 0) {
            notifyDataSetChanged();
        }
    }

    public List<String> getSelectedImages() {
        return mSelectedImages;
    }

    public List<Image> getImages() {
        return mImages;
    }

    private Image getImageByPath(String path) {
        if (mImages != null && mImages.size() > 0) {
            for (Image image : mImages) {
                if (image.path.equalsIgnoreCase(path)) {
                    return image;
                }
            }
        }
        return null;
    }

    /**
     * 设置数据集
     *
     * @param images 选择列表
     */
    public void setData(List<Image> images) {
        mSelectedImages.clear();
        if (images != null && images.size() > 0) {
            mImages = images;
        } else {
            mImages.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (showCamera) {
            return position == 0 ? TYPE_CAMERA : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getCount() {
        return showCamera ? mImages.size() + 1 : mImages.size();
    }

    @Override
    public Image getItem(int i) {
        if (showCamera) {
            if (i == 0) {
                return null;
            }
            return mImages.get(i - 1);
        } else {
            return mImages.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (isShowCamera()) {
            if (i == 0) {
                view = mInflater.inflate(R.layout.mis_list_item_camera, viewGroup, false);
                return view;
            }
        }
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.mis_list_item_image, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (holder != null) {
            holder.bindData(getItem(i));
            holder.check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EventData(Constant.IMAGE_SELECT_ADD, mImages.get(isShowCamera() ? i - 1 : i)));
                }
            });
        }

        return view;
    }

    private class ViewHolder {
        private ImageView image;
        private ImageView indicator;
        private View mask;
        private View check;

        ViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.image);
            indicator = (ImageView) view.findViewById(R.id.checkmark);
            check = view.findViewById(R.id.check);
            image.setLayoutParams(params);
            mask = view.findViewById(R.id.mask);
            mask.setLayoutParams(params);
            view.setTag(this);
        }

        void bindData(final Image data) {
            if (data == null) {
                return;
            }
            // 处理单选和多选状态
            if (showSelectIndicator) {
                indicator.setVisibility(View.VISIBLE);
                if (mSelectedImages.contains(data.path)) {
                    // 设置选中状态
                    indicator.setImageResource(R.drawable.mis_check_unselected);
                    mask.setVisibility(View.VISIBLE);
                } else {
                    // 未选择
                    indicator.setImageResource(R.drawable.mis_check_selected);
                    mask.setVisibility(View.GONE);
                }
            } else {
                indicator.setVisibility(View.GONE);
            }
            File imageFile = new File(data.path);
            if (imageFile.exists()) {

                ImageLoader imageLoader = new ImageLoader.Builder().url("file://" + data.path).placeHolder(R.color.black).
                        imgView(image).size(30, 30).build();
                ImageLoaderUtil.getInstance().loadImage(mContext, imageLoader);
            } else {
                image.setImageResource(R.drawable.mis_default_error);
            }
        }
    }

}
