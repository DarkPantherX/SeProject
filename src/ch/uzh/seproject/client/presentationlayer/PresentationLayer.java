package ch.uzh.seproject.client.presentationlayer;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;

import ch.uzh.seproject.client.businesslogiclayer.BusinessLogicLayer;
import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;

/**
 * This is the main inteface for the Presentation-Layer.
 */
public class PresentationLayer implements EntryPoint{
	// business-logic-layer
	private BusinessLogicLayer bll = new BusinessLogicLayer();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		drawUI();
	}


	/**
	 * Constructor
	 */
	public PresentationLayer() {
		
	}

	/**
	 * Draw the whole UI
	 */
	public void drawUI() {
		// create dialogbox
		final DialogBox out = new DialogBox();
		out.setText("Hello World");
		out.center();
		
		// send some data in database
		bll.exampleSaveData();
		// get data back
		bll.exampleGetData(new AsyncCallback<List<WeatherRecord>>() {
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<WeatherRecord> result) {
				for (WeatherRecord tmp : result) {
					// show data in dialogbox
					out.setText("city:  " + tmp.getCity());
				}
			}
		});
	}

}
