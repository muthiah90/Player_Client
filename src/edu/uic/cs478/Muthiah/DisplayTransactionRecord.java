package edu.uic.cs478.Muthiah;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplayTransactionRecord extends Activity {

	/**
	 * This is the onCreate method of the activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Getting the intent from the parent application
		Intent intent = getIntent();

		// Setting the table layout
		TableLayout tableLayout = new TableLayout(getApplicationContext());
		tableLayout.setLayoutParams(new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT,
				TableLayout.LayoutParams.MATCH_PARENT));

		// Getting the list of transaction records from the intent extra
		ArrayList<String> displayList = intent
				.getStringArrayListExtra("RETRIEVED_DATA");

		// Setting up the column header for the first column
		TableRow headerRow = new TableRow(this);
		TextView c0TextView = new TextView(this);
		c0TextView.setText("R#");
		headerRow.addView(c0TextView);

		// Setting up the column header for the second column
		TextView c1TextView = new TextView(this);
		c1TextView.setText(" Req Typ");
		headerRow.addView(c1TextView);

		// Setting up the column header for the third column
		TextView c2TextView = new TextView(this);
		c2TextView.setText(" Current State");
		headerRow.addView(c2TextView);

		// Setting up the column header for the fourth column
		TextView c3TextView = new TextView(this);
		c3TextView.setText(" Clp#");
		headerRow.addView(c3TextView);

		// Setting up the column header for the fifth column
		TextView c4TextView = new TextView(this);
		c4TextView.setText(" Date");
		headerRow.addView(c4TextView);

		// Adding the row to the table layout
		tableLayout.addView(headerRow);

		// Iterating over the list, through the containing number of rows
		for (int i = 0; i < displayList.size(); i++) {

			// Splitting all the data into columns based on comma
			String[] splitString = displayList.get(i).split(",");
			TableRow row = new TableRow(this);

			// Iterating through the strings corresponding to every other column
			// and adding the same into a table row
			for (int j = 0; j < 5; j++) {
				TextView textView = new TextView(this);
				textView.setText(splitString[j]);
				row.addView(textView);
			}

			// Finally adding the table row into the table layout
			tableLayout.addView(row);
		}

		// Creating a scroll view and inserting the table layout into the scroll
		// view so that data when data flows beyond the screen, it can be viewed
		// by scrolling down
		ScrollView scroll = new ScrollView(this);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		scroll.addView(tableLayout);

		// Setting the scroll view into the content view
		setContentView(scroll);
	}

}
