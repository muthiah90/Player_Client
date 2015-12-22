package edu.uic.cs478.Muthiah;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StopBroadcastReceiver extends BroadcastReceiver {

	/**
	 * This On Receive method gets executed as soon as a broadcasted intent is
	 * received
	 */
	@Override
	public void onReceive(Context context, Intent intent) {

		// This creates a toast stating the completion of the current track in
		// the music player
		Toast.makeText(context, "Selected track has finished playing",
				Toast.LENGTH_SHORT).show();
	}

}
