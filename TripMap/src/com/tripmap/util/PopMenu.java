package com.tripmap.util;

import java.util.ArrayList;

import com.tripmap.adapter.GirdViewAdapter;
import com.tripmap.getlocation.GetLocationActivity;
import com.tripmap.getlocation.NearbyInfoActivity;
import com.tripmap.mainfragment.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;


public class PopMenu {

	private Context context;
	private PopupWindow popupWindow;
	private ViewPager viewPager;
	private ArrayList<View> listViews;
	private int screenwidth;

	private int currentView = 0;// 当前视图
	private int viewOffset;// 动画图片偏移量
	private int imgWidth;// 图片宽度
	private TextView tv_main;
	private String choice = "";
	private GetLocationActivity activity;
	public PopMenu(Context context,GetLocationActivity activity) {
		this.activity = activity;
		this.context = context;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View view = inflater.inflate(R.layout.popmenu, null);

		tv_main = (TextView) view.findViewById(R.id.tv_main);
		this.tv_main.setOnClickListener(new myOnClick(0));
		setCursorWidth();

		viewPager = (ViewPager) view.findViewById(R.id.viewPagerw);
		viewPager.setFocusableInTouchMode(true);
		viewPager.setFocusable(true);

		listViews = new ArrayList<View>();
		listViews.add(inflater.inflate(R.layout.grid_menu, null));
		viewPager.setAdapter(new myPagerAdapter());

		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, context
				.getResources().getDimensionPixelSize(R.dimen.popmenu_h));
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	public void setCursorWidth() {
		screenwidth = getScreenWidth();
		imgWidth = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.img_cursor).getWidth();// 获取图片宽度
		viewOffset = (screenwidth / 3 - imgWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(viewOffset, 0);
		Log.e("TAG", screenwidth + "");

	}
	
	public int getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		int screenW = dm.widthPixels;
		return screenW;

	}

	// 下拉式 弹出 pop菜单 parent 右下角
	public void show(View parent) {
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 70);// 距离底部的位置
		popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}

	public void dismiss() {
		popupWindow.dismiss();
	}

	public class myPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {

			((ViewPager) arg0).removeView(listViews.get(arg1));

		}

		@Override
		public int getCount() {

			return listViews.size();

		}

		@SuppressLint({ "ParserError", "ParserError" })
		public Object instantiateItem(View arg0, int arg1) {

			if (arg1 < 3) {
				((ViewPager) arg0).addView(listViews.get(arg1 % 3), 0);

			}
			// 将来添加菜单的时候 新建一个gridviewadapter 然后new个gridview 添加到这里就可以
			GirdViewAdapter girdViewAdapter = new GirdViewAdapter(context);
			switch (arg1) {
			case 0:// 选项卡1

				GridView gridView = (GridView) arg0
						.findViewById(R.id.myGridView);

				gridView.setAdapter(girdViewAdapter);

				gridView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View v,
							int pasition, long id) {

						switch (pasition) {
						case 0:
							cateringDialog();
							break;
						case 1:
							shoppingDialog();
							break;
						case 2:
							bankDialog();
							break;
						case 3:
							trafficDialog();
							break;
						case 4:
							stayDialog();
							break;
						case 5:
							liveDialog();
							break;
						case 6:
							entertainmentDialog();
							break;
						case 7:
							pubFacilitiesDilog();
							break;
						case 8:
							carServiceDialog();
							break;
						case 9:
							governmentDialog();
							break;
						}

					}
				});
				break;
			}

			return listViews.get(arg1);

		}

		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == (arg1);

		}

	}

	private void cateringDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("查询");
		builder.setSingleChoiceItems(new String[] { "餐厅", "中餐厅", "快餐厅", "咖啡厅",
				"蛋糕房", "肯德基", "麦当劳", "必胜客", "自助餐" }, 0,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							choice = "餐厅";
						} else if (which == 1) {
							choice = "中餐厅";
						}else if (which == 2) {
							choice = "快餐厅";
						}else if (which == 3) {
							choice = "咖啡厅";
						}else if (which == 4) {
							choice = "蛋糕房";
						}else if (which == 5) {
							choice = "肯德基";
						}else if (which == 6) {
							choice = "麦当劳";
						}else if (which == 7) {
							choice = "必胜客";
						}else if (which == 8) {
							choice = "自助餐";
						}
					}
				});
		builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				System.out.println(choice);
				startMyActivity(choice);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private void shoppingDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("查询");
		builder.setSingleChoiceItems(new String[] { "商场", "超市", "书店", "市场" },
				0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							choice = "商场";
						} else if (which == 1) {
							choice = "超市";
						}else if (which == 2) {
							choice = "书店";
						}else if (which == 3) {
							choice = "市场";
						}
					}
				});
		builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				System.out.println(choice);
				startMyActivity(choice);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	private void bankDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("查询");
		builder.setSingleChoiceItems(new String[] { "银行", "ATM", "招商银行", "工商银行"
				, "中国银行", "建设银行", "农业银行", "交通银行", "邮政储蓄"},
				0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							choice = "银行";
						} else if (which == 1) {
							choice = "ATM";
						}else if (which == 2) {
							choice = "招商银行";
						}else if (which == 3) {
							choice = "工商银行";
						}else if (which == 4) {
							choice = "中国银行";
						}else if (which == 5) {
							choice = "建设银行";
						}else if (which == 6) {
							choice = "农业银行";
						}else if (which == 7) {
							choice = "交通银行";
						}else if (which == 8) {
							choice = "邮政储蓄";
						}
					}
				});
		builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				System.out.println(choice);
				startMyActivity(choice);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	private void trafficDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("查询");
		builder.setSingleChoiceItems(new String[] { "机场", "火车站", "公交站", "停车场"
				, "轻轨站", "地铁站", "长途汽车站", "火车票代售点", "飞机票代售点"},
				0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							choice = "机场";
						} else if (which == 1) {
							choice = "火车站";
						}else if (which == 2) {
							choice = "公交站";
						}else if (which == 3) {
							choice = "停车场";
						}else if (which == 4) {
							choice = "轻轨站";
						}else if (which == 5) {
							choice = "地铁站";
						}else if (which == 6) {
							choice = "长途汽车站";
						}else if (which == 7) {
							choice = "火车票代售点";
						}else if (which == 8) {
							choice = "飞机票代售点";
						}
					}
				});
		builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				System.out.println(choice);
				startMyActivity(choice);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	private void stayDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("查询");
		builder.setSingleChoiceItems(new String[] { "饭店", "酒店", "宾馆", "旅馆"},
				0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							choice = "饭店";
						} else if (which == 1) {
							choice = "酒店";
						}else if (which == 2) {
							choice = "宾馆";
						}else if (which == 3) {
							choice = "旅馆";
						}
					}
				});
		builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				System.out.println(choice);
				startMyActivity(choice);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	private void liveDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("查询");
		builder.setSingleChoiceItems(new String[] { "学校", "邮局", "药店", "医院"
				, "图书馆", "移动营业厅", "联通营业厅", "电信营业厅", "美容美发", "摄影冲印"},
				0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							choice = "学校";
						} else if (which == 1) {
							choice = "邮局";
						}else if (which == 2) {
							choice = "药店";
						}else if (which == 3) {
							choice = "医院";
						}else if (which == 4) {
							choice = "图书馆";
						}else if (which == 5) {
							choice = "移动营业厅";
						}else if (which == 6) {
							choice = "联通营业厅";
						}else if (which == 7) {
							choice = "电信营业厅";
						}else if(which == 8){
							choice = "美容美发";
						}else if (which == 9) {
							choice = "摄影冲印";
						}
					}
				});
		builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				System.out.println(choice);
				startMyActivity(choice);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	private void entertainmentDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("查询");
		builder.setSingleChoiceItems(new String[] { "电影院", "KTV", "洗浴", "网吧"
				, "台球厅", "酒吧", "茶馆", "景点" },
				0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							choice = "电影院";
						} else if (which == 1) {
							choice = "KTV";
						}else if (which == 2) {
							choice = "洗浴";
						}else if (which == 3) {
							choice = "网吧";
						}else if (which == 4) {
							choice = "台球厅";
						}else if (which == 5) {
							choice = "酒吧";
						}else if (which == 6) {
							choice = "茶馆";
						}else if (which == 7) {
							choice = "景点";
						}
					}
				});
		builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				System.out.println(choice);
				startMyActivity(choice);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	private void pubFacilitiesDilog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("查询");
		builder.setSingleChoiceItems(new String[] { "公厕", "公园", "公交站", "轻轨站"
				, "地铁站"},
				0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							choice = "公厕";
						} else if (which == 1) {
							choice = "公园";
						}else if (which == 2) {
							choice = "公交站";
						}else if (which == 3) {
							choice = "轻轨站";
						}else if (which == 4) {
							choice = "地铁站";
						}
					}
				});
		builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				System.out.println(choice);
				startMyActivity(choice);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	private void carServiceDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("查询");
		builder.setSingleChoiceItems(new String[] { "加油站", "停车场", "汽车维修", "汽车服务"},
				0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							choice = "加油站";
						} else if (which == 1) {
							choice = "停车场";
						}else if (which == 2) {
							choice = "汽车维修";
						}else if (which == 3) {
							choice = "汽车服务";
						}
					}
				});
		builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				System.out.println(choice);
				startMyActivity(choice);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	private void governmentDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("查询");
		builder.setSingleChoiceItems(new String[] { "市政府", "公安局", "消防局", "街道办"
				, "法院" },
				0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							choice = "市政府";
						} else if (which == 1) {
							choice = "公安局";
						}else if (which == 2) {
							choice = "消防局";
						}else if (which == 3) {
							choice = "街道办";
						}else if (which == 4) {
							choice = "法院";
						}
					}
				});
		builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				startMyActivity(choice);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	/*
	 * 
	 * 对选项卡单击监听的实现方法
	 */
	public class myOnClick implements View.OnClickListener {

		int index = 0;

		public myOnClick(int currentIndex) {

			index = currentIndex;
		}

		public void onClick(View v) {

			viewPager.setCurrentItem(index);

		}

	}
	private void startMyActivity(String choice){
		Intent intent = new Intent();
		intent.setClass(context, NearbyInfoActivity.class);
		intent.putExtra("choise", choice);
		context.startActivity(intent);
		activity.finish();
	}
	
}
