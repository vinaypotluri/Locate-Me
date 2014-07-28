package com.example.glass_loc;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.android.glass.app.Card;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;



public class MainActivity extends Activity implements OnInitListener {
	
	boolean isGPSEnabled = false,isNETWORKEnabled = false;
	TextView latlong, address;
	LocationListener mlocListener;
	LocationManager mlocManager,mgr;
	Locale locale;
	Location location;
	TextToSpeech tts;
    String string="";
    
	private TextView lat,lon,myAddress,info;

//  GPSTracker gps;
    String City,Street,Country,College;
    URL myurl;
   
    String[] pk={"5603","5265","5221","5210","purdy","kresge","otl","cass","kirby","prentis","deroy auditorium","rands","recreation"};
    String[] ugl={"5150","5104","5048","695","Anthony Wayne", "merrick"};
    String[] shiffman={"320","259","4325","4646","4400","Canfield","brush","willis ave","lous","applebaum"};
    String[] arthur={"474","495","45","5557","Ferry","palmer","gilmour"};
    String lib_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_main);
	
	latlong=(TextView) findViewById(R.id.textView1);
	myAddress=(TextView) findViewById(R.id.textView2);
	info=(TextView) findViewById(R.id.textView3);
	latlong.setText("some text here");

	//text-to-speech
	 tts = new TextToSpeech(this, this);
	 
//	mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	
	double latitude = 0.0;
	double longitude = 0.0;
	latlong.setText("latitude:    " + latitude + "\n" + "longitude: " + longitude);

	
	//using dynamic set of location values
	
	Criteria criteria = new Criteria();
	criteria.setAccuracy(Criteria.ACCURACY_COARSE);

    
//	criteria.setAltitudeRequired(true);
	mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
	
	List<String> providers = mgr.getProviders(criteria, true);
	mlocListener = new MyLocationListener();

	for (String provider : providers) {
	    mgr.requestLocationUpdates(provider, 0,0, mlocListener);
	    
	}
	
//	mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, mlocListener);
}
	@Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
	

	 @Override
     public void onPause()
     {
             super.onPause();
             mgr.removeUpdates(mlocListener);
     }

	  public class MyLocationListener implements LocationListener
	  {

	  @Override
	  public void onLocationChanged(Location loc)
	  {		  
		  latlong=(TextView) findViewById(R.id.textView1);
		  address=(TextView) findViewById(R.id.textView2);
		  
		  latlong.setText("latitude:1 " +"\n" + "longitude:2 ");

		  
	    double latitude = loc.getLatitude();
	    double longitude = loc.getLongitude();
    

	    //values are network set either wifi or mobile - automatic this time
		latlong.setText("latitude: " + latitude + "\n"  + "longitude: " + longitude );
//    latlong.setText("latitude: " + Double.toString(latitude) + "\n" + "longitude: " + Double.toString(longitude));
	  
        
        myAsyncTask task = new myAsyncTask();
        task.execute();
        

    Geocoder geocoder= new Geocoder(MainActivity.this,Locale.getDefault());
    
    //try block
    try {
		List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
		
		if(addresses != null)
		{													
			
			
               Street = addresses.get(0).getAddressLine(0);                                                          
               City = addresses.get(0).getAddressLine(1);
               Country = addresses.get(0).getAddressLine(2);


             
               //check street name
               for(int i=0;i<pk.length;i++)                            
               if(Street.toLowerCase().contains(pk[i].toLowerCase()))
               {
                      lib_name="pk";
               }
               
               for(int i=0;i<ugl.length;i++)       
               if(Street.toLowerCase().contains(ugl[i].toLowerCase()))
               {
                       lib_name="ugl";
               }
             
               for(int i=0;i<shiffman.length;i++)  
               if(Street.toLowerCase().contains(shiffman[i].toLowerCase()))
               {
                       lib_name="shiffman";
               }                               
               for(int i=0;i<arthur.length;i++)            
               if(Street.toLowerCase().contains(arthur[i].toLowerCase()))
               {
                       lib_name="arthur";
               }                               

               
               switch(lib_name)
               {
               
               case "pk": myAddress.setText("Purdy / Kresge Library" +"\n" +Street+ "\n" + City +" "+Country);
               //voice command
               break;
               case "ugl":myAddress.setText("Undergraduate Library " +"\n" +Street+ "\n" + City +" "+Country);
               //voice command
               break;
               case "shiffman":myAddress.setText("Shiffman Library" +"\n" +Street+ "\n" + City +" "+Country);
               //voice command
               break;
               case "arthur":myAddress.setText("Arthur Library" +"\n" +Street+ "\n" + City +" "+Country);
               //voice command
               break;
               default: myAddress.setText("you are at here" +"\n" +Street+ "\n" + City +" "+Country);
               }
                                                              
 }
   

 
    else
    {
   myAddress.setText("No Address returned!");
 }
           
}

//catch block for exception
catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    myAddress.setText("Address Unavailable!");
}

   

}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	  }

	
	
	
	/// Code for the Menu ///
	
	//creates a menu calling the menu xml file
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //handles users input
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stop:
                stopService(new Intent(this, MainActivity.class));
                finish();
                return true;
            case R.id.info:
            	Intent info=new Intent(this,info.class);
            	startActivity(info);
            	return true;            	
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/* 
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        // Nothing else to do, closing the activity.
        finish();
    }
 */   
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
          if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
              openOptionsMenu();
              return true;
          }
          return false;
    }
    
    
    //my Asynchrnous Task
    private class myAsyncTask extends AsyncTask<String, String, String>
    {

          @Override
          protected void onPreExecute()
          {
                   
          }
           
          @Override
          protected String doInBackground(String... params)
          {
        		info=(TextView) findViewById(R.id.textView3);
//                  publishProgress("loading library details");
                  // TODO Auto-generated method stub
                  try {
                          Document doc_time = Jsoup.connect("http://www.library.wayne.edu/test/elliot/hours.php").get();
                          Document doc_comp = Jsoup.connect("http://www.library.wayne.edu/test/elliot/computers.php").get();
                         
                          //time
                          Elements arthur_time  = doc_time.select("div.featured-resource > div.module > ul > li > a[href=/info/hours/calendar.php?cal=law]>span" );
                          Elements purdy_time  = doc_time.select("div.featured-resource > div.module > ul > li > a[href=/info/hours/calendar.php?cal=pk]>span" );
                          Elements shiffman_time  = doc_time.select("div.featured-resource > div.module > ul > li > a[href=/info/hours/calendar.php?cal=mec]>span" );
                          Elements ugl_time  = doc_time.select("div.featured-resource > div.module > ul > li > a[href=/info/hours/calendar.php?cal=ugl]>span" );
                         

                         
                          //computers
                          Elements purdy_comp  = doc_comp.select("div.computer-availability > ul > li.pk-computers>span" );
                          Elements shiffman_comp  = doc_comp.select("div.computer-availability > ul > li.med-computers>span" );
                          Elements ugl_comp  = doc_comp.select("div.computer-availability > ul > li.ugl-computers>span" );

//                        publishProgress("Timings: " + "\t" +purdy_time.text()+ "\n" + "Computers:" +"\t" + purdy_comp.text());
                          if(string != lib_name)
                     	 {
                     	 string=lib_name;
                     	 
                          switch(lib_name)
                          {
                             case "pk":  // purdy/kresge
                            	 
                            	 if(string == lib_name)
                            	 {
                            	 string=lib_name;
                            	 publishProgress("Hours :       " + "\t" +purdy_time.text()+ "\n" + "Computers Available : " +"\t" + purdy_comp.text());
	                             //voice command
	                             String[] c1=(purdy_comp.text()).split("/");
	                             speak("welcome to the purdy kresge library. "+ " we are open today from " +purdy_time.text() + ". we have"+ c1[0]+ " computers available");
                            	 }
                             break;
                             
                             case "ugl":
                            	 if(string == lib_name)
                            	 {
                            	 string=lib_name;
                            	 
                            	 publishProgress("Hours :       " + "\t" +ugl_time.text()+ "\n" + ". Computers Available : " +"\t" + ugl_comp.text());
                             //voice command
                             String[] c2=(ugl_comp.text()).split("/");
                             speak("welcome to the Undergraduate library. "+ " we are open today from " +ugl_time.text()+". we have"+ c2[0]+ " computers available");
                            	 }
                             break;
                             
                             case "shiffman":
                            	 if(string == lib_name)
                            	 {
                            	 string=lib_name;
                            	 
                            	 publishProgress("Hours :       " + "\t" +shiffman_time.text()+ "\n" + ". Computers Available : " +"\t" + shiffman_comp.text());
                             //voice command
                             String[] c3=(shiffman_comp.text()).split("/");
                             speak("welcome to the shiffman medical library. "+ " we are open today from " +shiffman_time.text()+". we have"+ c3[0]+ " computers available");
                            	 }
                            	 break;
                             
                             case "arthur":
                            	 if(string == lib_name)
                            	 {
                            	 string=lib_name;
                            	 
                            	 publishProgress("Hours :       " + "\t" +arthur_time.text()+ "\n" + "No Computer Info");
                             //voice command                             
                             speak("welcome to the Arthur neef law library. "+ " we are open today from " +arthur_time.text());
                            	 }
                            	 break;
                             
                             default: myAddress.setText("nothing to display");
                          } //end of switch case
                     	 } //end of if loop
                  } catch (IOException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                  }
                  
                  return null;

          }
         
          @Override
          protected void onProgressUpdate(String... text)
          {                 
                          info.setText(text[0]);                                  
          }
           
          @Override
          protected void onPostExecute(String result)
          {
                 
          }
         
    }

    



	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
		tts.setLanguage(Locale.getDefault());
//		speak("");
		
	}
	private void speak(String text) {
		// TODO Auto-generated method stub
		tts.speak("Hello Google glass user."+text, TextToSpeech.QUEUE_ADD, null);
	}
        
}

