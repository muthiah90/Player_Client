package edu.uic.cs478.Muthiah;

import java.util.ArrayList;
import java.util.List;

import edu.uic.cs478.Muthiah.Service.MusicKey;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

public class PlayerClient extends Activity {

	// Variable declarations for API's and bound variable
	private MusicKey mMusicKey;
	private boolean mIsBound;

	/**
	 * On Create method of the activity
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.main);

		// Check if the activity is already bound to the service or not
		if (!mIsBound) {

			// Setting the intent to point to the class implementing the aidl
			// and then binding to the service
			Intent intent = new Intent(MusicKey.class.getName());
			bindService(intent, this.mConnection, Context.BIND_AUTO_CREATE);

		}

	}

	/**
	 * On Resume method of the activity
	 */
	@Override
	protected void onResume() {
		super.onResume();

		// Check if the activity is already bound to the service or not
		if (!mIsBound) {

			// Setting the intent to point to the class implementing the aidl
			// and then binding to the service
			Intent intent = new Intent(MusicKey.class.getName());
			bindService(intent, this.mConnection, Context.BIND_AUTO_CREATE);

		}
	}

	/**
	 * On Pause method of the activity
	 */
	@Override
	protected void onPause() {

		super.onPause();
	}

	/**
	 * On Destroy method of the activity
	 */
	@Override
	protected void onDestroy() {

		// Check if the activity is bound to the service
		if (mIsBound) {
			unbindService(this.mConnection);
		}
		super.onDestroy();
	}

	/**
	 * Method which reacts to the track selection
	 * 
	 * @param w
	 */
	public void PlayMusic(View w) {

		// Tag refers to the selected track
		int viewTag = Integer.parseInt(((String) w.getTag()));
		try {
			if (mIsBound) {
				// Starting the music corresponding to the selected track
				mMusicKey.startMusic(viewTag);
			}
		} catch (RemoteException e) {
			System.out.println("Remote Exception in Start Music");
		}

	}

	/**
	 * This method reacts to the stop selection of the user
	 * 
	 * @param w
	 */
	public void StopMusic(View w) {
		try {
			if (mIsBound) {
				mMusicKey.stopMusic();
			}
		} catch (RemoteException e) {
			System.out.println("Remote Exception in Stop Music");
		}
	}

	/**
	 * This method reacts to the pause selection of the user
	 * 
	 * @param w
	 */
	public void PauseMusic(View w) {
		try {
			if (mIsBound) {
				mMusicKey.pauseMusic();
			}
		} catch (RemoteException e) {
			System.out.println("Remote Exception in Pause Music");
		}
	}

	/**
	 * This method reacts to the resume selection of the user This works only
	 * when the user had already paused a music
	 * 
	 * @param w
	 */
	public void ResumeMusic(View w) {
		try {
			if (mIsBound) {
				mMusicKey.resumePlay();
			}
		} catch (RemoteException e) {
			System.out.println("Remote Exception in Pause Music");
		}
	}

	/**
	 * This method reacts to the transaction record selection by the user in the
	 * main activity
	 * 
	 * @param w
	 */
	public void GetData(View w) {

		// Initializing a list to receive from the service application
		List<String> receivedData = new ArrayList<String>();
		try {

			// Getting the transaction record from the server application
			receivedData = mMusicKey.readTransactionData();

			// Converting the List into an ArrayList
			ArrayList<String> tempArrayList = new ArrayList<String>(
					receivedData.size());
			tempArrayList.addAll(receivedData);

			// Creating an intent for creating a new activity to show the
			// transaction record
			Intent intent = new Intent(PlayerClient.this,
					DisplayTransactionRecord.class);

			// Adding the received list of strings to intent extra
			intent.putStringArrayListExtra("RETRIEVED_DATA", tempArrayList);
			// Starting the activity
			startActivity(intent);
		} catch (RemoteException e) {
			System.out.println("Remote Exception while reading data");
		}
	}

	// This is used to get the service interface after the application binds
	// with the server application
	private final ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder iservice) {

			// Getting the API service from the server application and setting
			// the bind variable to true
			mMusicKey = MusicKey.Stub.asInterface(iservice);

			mIsBound = true;

		}

		// This is called when the application disconnects itself from the
		// server application
		public void onServiceDisconnected(ComponentName className) {

			// The API service is set to null and the bond variable is also
			// reset to 0
			mMusicKey = null;

			mIsBound = false;

		}
	};
}
