package com.bingstar.bingmall.sdk.map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/8 下午7:49
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/8 下午7:49
 * @modify by reason:{方法名}:{原因}
 */

public class AutoArrayAdapter extends ArrayAdapter<MapAddrBean> {

    private ArrayList<MapAddrBean> mOriginalValues;

    private final Object mLock = new Object();

    private List<MapAddrBean> mObjects;

    private ArrayFilter mFilter;

    public AutoArrayAdapter(Context context, int resource, List<MapAddrBean> objects) {
        super(context, resource, objects);
        mObjects = objects;
    }

    @Override
    public void add(MapAddrBean object) {
        super.add(object);
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.add(object);
            }
        }
    }

    @Override
    public void addAll(Collection<? extends MapAddrBean> collection) {
        super.addAll(collection);
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.addAll(collection);
            }
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public void addAll(MapAddrBean... items) {
        super.addAll(items);
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Collections.addAll(mOriginalValues, items);
            }
        }
    }

    @Override
    public void insert(MapAddrBean object, int index) {
        super.insert(object, index);
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.add(index, object);
            }
        }
    }

    @Override
    public void remove(MapAddrBean object) {
        super.remove(object);
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.remove(object);
            }
        }
    }

    @Override
    public void clear() {
        super.clear();
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.clear();
            }
        }
    }

    @Override
    public void sort(Comparator<? super MapAddrBean> comparator) {
        super.sort(comparator);
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Collections.sort(mOriginalValues, comparator);
            }
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(mObjects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                final ArrayList<MapAddrBean> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final ArrayList<MapAddrBean> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<MapAddrBean> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final MapAddrBean value = values.get(i);
                    final String valueText = value.getAddress().toLowerCase();
                    // First match against the whole, non-splitted value
                    if (valueText.contains(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            mObjects = (List<MapAddrBean>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            if (resultValue==null){
                return null;
            }else {
               return resultValue.toString().split("------")[0];
            }
        }
    }
}
