package com.osi.newsupdates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.osi.newsupdate.listeners.ResponseListner;
import com.osi.newsupdate.listeners.Responseerrorlistner;
import com.osi.newsupdate.models.Cache;
import com.osi.newsupdate.models.Category;
import com.osi.newsupdate.models.Channel;
import com.osi.newsupdate.models.Newsitems;
import com.osi.newsupdates.db.DbHelper;
import com.osi.newsupdates.tables.Cachetable;
import com.osi.newsupdates.tables.Categorytable;
import com.osi.newsupdates.tables.Channeltable;
import com.osi.newsupdates.utils.SharedPrefs;
import com.osi.newsupdates.utils.TransparentProgressDialog;
import com.osi.newupdate.adapters.CategoriesListAdapter;
import com.osi.newupdate.adapters.ManageCategoryAdapter;
import com.osi.newupdate.adapters.MenuItemListAdapter;
import com.osi.newupdate.adapters.NewsAdapter;

public class NewsActivity extends ActionBarActivity {


	public RequestQueue requestQueue;
	public ImageLoader iml;
	private Dialog adchanneldilog,channlsettings,addcategorydialog,managecategorydialog,editcategorydialog;
	String mTitle = "";
	private ArrayList<Channel> mMenus;
	private ArrayList<String> mMenusurl;
	private Button addchannel_btn;
	public String categorynameholder,channelnameholder;
	int[] mListItemIcons = new int[]{
			R.drawable.my_account_icon,
			R.drawable.messages_icon,
			R.drawable.settings_icon,
			R.drawable.frnds_icon,
			R.drawable.india,
			R.drawable.nepal,R.drawable.frnds_icon,
			R.drawable.india,
			R.drawable.nepal
	};

	private RelativeLayout categorydropdown;
	private Channeltable channeltable;
	private Categorytable categorytable;
	private Cachetable cache;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private LinearLayout mDrawer ;
	private List<HashMap<String,String>> mList ;
	private MenuItemListAdapter mAdapter;
	final private String ITEM = "menuItemName";
	final private String ITEM_POSITION = "position";
	final private String ITEM_ICON = "icon";
	public ListView newslist;
	public NewsAdapter newsadapter;
	private ManageCategoryAdapter manageadapter;
	private CategoriesListAdapter catlistadapter;
	public TransparentProgressDialog pd;
	private ImageView menuButton;
	public static int selectedPosition = 0;

	private ArrayAdapter<String> categoryAdapter;
	private TextView category_tv;
	ArrayList<Category> categories_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);


		newslist=(ListView)findViewById(R.id.newslist);


		DbHelper DB = new DbHelper(NewsActivity.this);



		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowTitleEnabled(false);

		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.titlebar);

		menuButton = (ImageView) mActionBar.getCustomView().findViewById(R.id.titlebar_iv_menu);

		mMenusurl = new ArrayList<String>();
		mDrawerList = (ListView) findViewById(R.id.drawer_list);	 
		mDrawer = (LinearLayout) findViewById(R.id.drawer);	 
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		addchannel_btn =(Button)findViewById(R.id.addchannel_btn);

		categorydropdown =(RelativeLayout)findViewById(R.id.category_dropdown);
		category_tv=(TextView)findViewById(R.id.category_tv);

		if(SharedPrefs.getfirsttime(getApplicationContext())==false){
			
		channeltable= new Channeltable(NewsActivity.this);
		channeltable.insertchannel("The Hindu", "");
		channeltable.insertchannel("BBC News", "");
		channeltable.insertchannel("IBN Live", "");
		channeltable.insertchannel("Zee News", "");
		
		
		
		//cahnneltable.getnamefromid(id)
		Channel ch =channeltable.getChannelfromname("The Hindu");
		categorytable = new Categorytable(NewsActivity.this);
		categorytable.insertcategory("International", "http://www.hindu.com/rss/03hdline.xml",ch.getId());
		categorytable.insertcategory("National", "http://www.hindu.com/rss/02hdline.xml",ch.getId());
		categorytable.insertcategory("Business", "http://www.hindu.com/rss/06hdline.xml",ch.getId());
		categorytable.insertcategory("Weather", "http://www.hindu.com/rss/10hdline.xml",ch.getId());
		
		Channel chBBC =channeltable.getChannelfromname("BBC News");
		categorytable = new Categorytable(NewsActivity.this);
		categorytable.insertcategory("International", "http://feeds.bbci.co.uk/news/world/rss.xml",chBBC.getId());
		categorytable.insertcategory("TopStories", "http://feeds.bbci.co.uk/news/rss.xml",chBBC.getId());
		categorytable.insertcategory("Business", "http://feeds.bbci.co.uk/news/business/rss.xml",chBBC.getId());
		categorytable.insertcategory("Technology", "http://feeds.bbci.co.uk/news/technology/rss.xml",chBBC.getId());
		categorytable.insertcategory("Health", "http://feeds.bbci.co.uk/news/health/rss.xml",chBBC.getId());
		categorytable.insertcategory("Poltics", "http://feeds.bbci.co.uk/news/politics/rss.xml",chBBC.getId());
		
		
		Channel chIbnlive =channeltable.getChannelfromname("IBN Live");
		categorytable = new Categorytable(NewsActivity.this);
		categorytable.insertcategory("Technology", "http://ibnlive.in.com/ibnrss/rss/tech/tech.xml",chIbnlive.getId());
		categorytable.insertcategory("Sports", "http://ibnlive.in.com/ibnrss/rss/sports/sports.xml",chIbnlive.getId());
		
		
		Channel chZeeNews =channeltable.getChannelfromname("Zee News");
		categorytable = new Categorytable(NewsActivity.this);
		categorytable.insertcategory("World", " http://zeenews.india.com/rss/world-news.xml",chZeeNews.getId());
		categorytable.insertcategory("National", "http://zeenews.india.com/rss/india-national-news.xml",chZeeNews.getId());
		categorytable.insertcategory("Bussiness", " http://zeenews.india.com/rss/business.xml",chZeeNews.getId());
		categorytable.insertcategory("Sports", "http://zeenews.india.com/rss/sports-news.xml",chZeeNews.getId());
		
		SharedPrefs.setfirsttime(getApplicationContext(), true);
		categorytable.close();
		channeltable.close();
		}
		setchannelsAdapter();

		displaycachememory();
		
		

		categorydropdown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				categorieslistdialog(categories_list);

			}
		});


		mDrawerList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Channel c = (Channel)mAdapter.getItem(position);



				channelSettingsdialog(c);


				return false;
			}
		});


		mDrawerList.setOnItemClickListener(new OnItemClickListener() {	 
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {		 
				//showFragment(position);


				//Log.d("Position: ", ""+mMenusurl[position]);
				//Sendrequest(mMenusurl[position],NewsActivity.this);

				Channel c = (Channel)mAdapter.getItem(position);

				setcategoriesdropdown(c);

				selectedPosition = position;
				mAdapter.setSelectedIndex(selectedPosition);
				mAdapter.notifyDataSetChanged();


				Log.d("Position: ", ""+position);
				// Closing the drawer
				mDrawerLayout.closeDrawer(mDrawer);
			}
		});




		menuButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mDrawerLayout.isDrawerOpen(mDrawer)){
					mDrawerLayout.closeDrawer(mDrawer);
				}else{
					mDrawerLayout.openDrawer(mDrawer);
				}
			}
		});

		addchannel_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				addnewchanneldialog();
			}
		});
		//Sendrequest("http://zeenews.india.com/rss/world-news.xml",NewsActivity.this,true);

			//Sendrequest("http://ibnlive.in.com/xml/rss/tech/tech.xml",NewsActivity.this);

		newslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Newsitems newsitems = (Newsitems)newsadapter.getItem(position);

				/*Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(newsitems.getNewslink()));
				startActivity(in);*/

				Intent intent = new Intent(NewsActivity.this,WebViewActivity.class);
				intent.putExtra("link", newsitems.getNewslink());
				Log.d("NEWSL", "NEWSL"+newsitems.getNewslink());
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				/*
				Intent intent = new Intent(NewsActivity.this,DetailsActivity.class);
				intent.putExtra("Title", newsitems.getTitle());
				intent.putExtra("Description", newsitems.getDescripetion());
				intent.putExtra("ImageUrl", newsitems.getImage());
				startActivity(intent);
				Log.d("List Position", "List Position"+position);
				 */

			}
		});

	}

	public void Sendrequest(String url,NewsActivity Nactivity,boolean ch)
	{

		if(ch==true)
		{
			pd = new TransparentProgressDialog(Nactivity, R.drawable.spinner);

			pd.show();
		}

		requestQueue = Volley.newRequestQueue(this);


		iml = new ImageLoader(requestQueue, new ImageCache() {

			private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

			public void putBitmap(String url, Bitmap bitmap) {

				mCache.put(url, bitmap);

			}

			@Override
			public Bitmap getBitmap(String url) {

				return mCache.get(url);
			}
		});

		StringRequest req = new StringRequest(Request.Method.POST,url, new ResponseListner(Nactivity)
		, new Responseerrorlistner(Nactivity));


		requestQueue.add(req);

	}


	public void addnewchanneldialog(){

		adchanneldilog = new Dialog(NewsActivity.this);

		adchanneldilog.setContentView(R.layout.addnew_channel);

		Button ok = (Button)adchanneldilog.findViewById(R.id.ok);
		Button cancel = (Button)adchanneldilog.findViewById(R.id.cancel);

		final EditText cahnnelnameedt= (EditText)adchanneldilog.findViewById(R.id.cahnnelname_edt);
		final EditText categoryedt= (EditText)adchanneldilog.findViewById(R.id.categorymname_edt);
		final EditText urledt= (EditText)adchanneldilog.findViewById(R.id.url_edt);
		//nametv.setText(name);
		//companytv.setText(company);

		adchanneldilog.setTitle("Add Channel");

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(cahnnelnameedt.getText().toString().trim().length()>0 && categoryedt.getText().toString().trim().length()>0
						&&  urledt.getText().toString().trim().length()>0)
				{
					channeltable = new Channeltable(NewsActivity.this);
					boolean check =channeltable.checkchannelname(cahnnelnameedt.getText().toString());
					if(check == true)
					{

						channeltable.insertchannel(cahnnelnameedt.getText().toString(), "");
						//cahnneltable.getnamefromid(id)
						Channel ch =channeltable.getChannelfromname(cahnnelnameedt.getText().toString());
						channeltable.close();


						categorytable = new Categorytable(NewsActivity.this);
						categorytable.insertcategory(categoryedt.getText().toString(), urledt.getText().toString(), ch.getId());
						categorytable.close();

						setchannelsAdapter();
						setcategoriesdropdown(ch);

					}else{
						Toast.makeText(getApplicationContext(), "Channel name already exists",Toast.LENGTH_SHORT).show();
					}


					adchanneldilog.cancel();

				}else
				{

					Toast.makeText(getApplicationContext(), "All fields are Mandatory",Toast.LENGTH_SHORT).show();

				}

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				adchanneldilog.cancel();

			}
		});
		adchanneldilog.show();

	}

	private void setcategoriesdropdown(Channel c)
	{

		categories_list = new ArrayList<Category>();

		categorytable = new Categorytable(NewsActivity.this);
		categories_list = categorytable.getdetailsfrmchnlid(c.getId());
		categorytable.close();

		channelnameholder = c.getChannelname();

		Category cc = (Category)categories_list.get(0);

		categorynameholder = cc.getCategoryname();

		category_tv.setText(categorynameholder);

		Category ca = (Category)categories_list.get(0);

		Sendrequest(ca.getCategoryurl(),NewsActivity.this,true);

	}



	private void setchannelsAdapter()
	{


		mMenus = new ArrayList<Channel>();

		Channeltable ch = new Channeltable(NewsActivity.this);

		mMenus=ch.getalldetails();

		mAdapter = new MenuItemListAdapter(this, mMenus);	 

		mDrawerList.setAdapter(mAdapter);

	}


	private void channelSettingsdialog(final Channel c){

		channlsettings = new Dialog(NewsActivity.this);
		View vi = getLayoutInflater().inflate(R.layout.channel_settingsdialog, null);



		channlsettings.setTitle(c.getChannelname());
		channlsettings.setContentView(vi);
		final ListView list_view = (ListView)vi.findViewById(R.id.ListView);

		List<String> list_items = new ArrayList<String>();
		list_items.add("Add Category");
		list_items.add("Manage Category");



		ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(NewsActivity.this, R.layout.simple_list, list_items);
		list_view.setAdapter(myarrayAdapter);

		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				String selectedItem = list_view.getItemAtPosition(position).toString().trim();

				if(selectedItem.equalsIgnoreCase("Add Category")){

					//copyDialog(qustionname);
					addcategorydialog(c);
					channlsettings.cancel();

				}else if(selectedItem.equalsIgnoreCase("Manage Category")){

					//moveDilog(questId, qustionname);
					manageCatgorydialog(c);
					channlsettings.cancel();
				}


			}
		});

		channlsettings.show();

	}


	private void addcategorydialog(final Channel c){

		addcategorydialog = new Dialog(NewsActivity.this);

		addcategorydialog.setContentView(R.layout.addcategory_dialog);

		Button ok = (Button)addcategorydialog.findViewById(R.id.ok);
		Button cancel = (Button)addcategorydialog.findViewById(R.id.cancel);

		final EditText categoryedt= (EditText)addcategorydialog.findViewById(R.id.categorymname_edt);
		final EditText urledt= (EditText)addcategorydialog.findViewById(R.id.url_edt);
		//nametv.setText(name);
		//companytv.setText(company);

		addcategorydialog.setTitle(c.getChannelname());

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(categoryedt.getText().toString().trim().length()>0 && urledt.getText().toString().trim().length()>0)
				{
					categorytable = new Categorytable(NewsActivity.this);
					categorytable.insertcategory(categoryedt.getText().toString(), urledt.getText().toString(),c.getId());
					categorytable.close();

					addcategorydialog.cancel();

					channelnameholder = c.getChannelname();
					categorynameholder = categoryedt.getText().toString();

					Sendrequest(urledt.getText().toString(),NewsActivity.this,true);

				}else
				{
					Toast.makeText(getApplicationContext(), "All fields are Mandatory",Toast.LENGTH_SHORT).show();
				}


			}

		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addcategorydialog.cancel();
			}

		});

		addcategorydialog.show();
	}

	private void manageCatgorydialog(final Channel c){


		managecategorydialog = new Dialog(NewsActivity.this);
		View vi = getLayoutInflater().inflate(R.layout.managecatgory_mainview, null);
		managecategorydialog.setTitle(c.getChannelname());
		managecategorydialog.setContentView(vi);
		final ListView list_view = (ListView)vi.findViewById(R.id.ListView);
		//dataList = to.getAllTopics();
		ArrayList<Category> dataList = new ArrayList<Category>();
		Categorytable category = new Categorytable(NewsActivity.this);
		dataList=category.getdetailsfrmchnlid(c.getId());
		manageadapter = new ManageCategoryAdapter(dataList,NewsActivity.this);
		list_view.setAdapter(manageadapter);


		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Category category= (Category)manageadapter.getItem(position);

				editcategorydialog(category.getId(),c.getChannelname(), category.getCategoryname(), category.getCategoryurl(),c.getId());

				managecategorydialog.cancel();



			}
		});

		managecategorydialog.show();


	}

	private void editcategorydialog(final int CategoryId,String ChannelName, String Categoryname,String CategoryUrl ,final int ChanelId){



		editcategorydialog = new Dialog(NewsActivity.this);

		editcategorydialog.setContentView(R.layout.addcategory_dialog);

		Button ok = (Button)editcategorydialog.findViewById(R.id.ok);
		Button cancel = (Button)editcategorydialog.findViewById(R.id.cancel);

		final EditText categoryedt= (EditText)editcategorydialog.findViewById(R.id.categorymname_edt);
		final EditText urledt= (EditText)editcategorydialog.findViewById(R.id.url_edt);
		categoryedt.setText(Categoryname);
		urledt.setText(CategoryUrl);

		editcategorydialog.setTitle(ChannelName);

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				categorytable = new Categorytable(NewsActivity.this);
				categorytable.updatecategory(CategoryId,categoryedt.getText().toString(), urledt.getText().toString(), ChanelId);
				categorytable.close();

				editcategorydialog.cancel();
			}

		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editcategorydialog.cancel();
			}

		});

		editcategorydialog.show();

	}


	public void displaycachememory()
	{

		cache = new Cachetable(NewsActivity.this);

		ArrayList<Newsitems> ni = cache.getdetails();
		Cache cc = cache.getnameandcatdetails();
		cache.close();
		if(ni!=null && ni.size()>0){
		

		newsadapter = new NewsAdapter(ni,NewsActivity.this);
		newslist.setAdapter(newsadapter);

		channelnameholder = cc.getChannelname();
		categorynameholder = cc.getCategoryname();

		channeltable= new Channeltable(NewsActivity.this);

		Channel c = channeltable.getChannelfromname(channelnameholder);

		categories_list = new ArrayList<Category>();

		categorytable = new Categorytable(NewsActivity.this);
		categories_list = categorytable.getdetailsfrmchnlid(c.getId());
		categorytable.close();

		channelnameholder = c.getChannelname();

		Category c1 = (Category)categories_list.get(0);

		categorynameholder = c1.getCategoryname();

		Category ca = (Category)categories_list.get(0);
		category_tv.setText(categorynameholder);

		channeltable.close();

		int id =mAdapter.getfirstindex(channelnameholder);

		MenuItemListAdapter.setSelectedIndex(id);



		Sendrequest(ca.getCategoryurl(),NewsActivity.this,false);

		}






	}

	private void categorieslistdialog(ArrayList<Category> datalist){


		managecategorydialog = new Dialog(NewsActivity.this);
		View vi = getLayoutInflater().inflate(R.layout.categorieslist_mainview, null);
		//managecategorydialog.setTitle("Categories");
		managecategorydialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		managecategorydialog.setContentView(vi);
		final ListView list_view = (ListView)vi.findViewById(R.id.ListView);

		catlistadapter = new CategoriesListAdapter(datalist,NewsActivity.this);
		if(datalist!=null&& datalist.size()>0){
			list_view.setAdapter(catlistadapter);
		}

		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Category category= (Category)catlistadapter.getItem(position);


				categorynameholder=category.getCategoryname();
				category_tv.setText(categorynameholder);

				Sendrequest(category.getCategoryurl(), NewsActivity.this,true);

				managecategorydialog.cancel();

			}
		});

		managecategorydialog.show();
	}


}
