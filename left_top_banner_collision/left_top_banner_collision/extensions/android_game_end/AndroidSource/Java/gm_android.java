package ${YYAndroidPackageName};

import com.yoyogames.runner.RunnerJNILib;
import 	java.util.concurrent.locks.ReentrantLock;
import ${YYAndroidPackageName}.RunnerActivity;
import ${YYAndroidPackageName}.R;
import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.inputmethod.*;
import android.util.Log;
import android.widget.TextView;
import android.widget.EditText;
import android.content.pm.PackageManager;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;

public class gm_android extends RunnerSocial{
	private static final int EVENT_OTHER_SOCIAL = 70;
	private final ReentrantLock lock = new ReentrantLock();

	@Override
	public void onResume(){
		if(RunnerActivity.CurrentActivity!=null){
			int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
			RunnerJNILib.DsMapAddString(dsMapIndex, "id", "afterResume" );
			RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
		}
	}

	public void game_end(){
		RunnerJNILib.ExitApplication();
	}


	public void sleep(double time){
		try {
            Thread.sleep((long)time);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
	}

	public String get_package_name(){
		return RunnerActivity.CurrentActivity.getPackageName();
	}

	public double check_installed(String pack){
		Boolean installed=false;
		PackageManager pm = RunnerActivity.CurrentActivity.getPackageManager();
		try{
            pm.getPackageInfo(pack,PackageManager.GET_ACTIVITIES);
            installed=true;
        }catch(Exception e){}
		if(installed)return 1.0;
		else return 0.0;
	}

	public void rate_us(){
		final ReviewManager manager = ReviewManagerFactory.create(RunnerActivity.CurrentActivity);
		manager.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
			@Override
			public void onComplete(Task<ReviewInfo> task) {
				if (task.isSuccessful()) {
					ReviewInfo reviewInfo = task.getResult();
					manager.launchReviewFlow(RunnerActivity.CurrentActivity, reviewInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
						@Override
						public void onComplete(Task<Void> task) {
							int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
							RunnerJNILib.DsMapAddString(dsMapIndex, "id", "android_rate_us" );
							RunnerJNILib.DsMapAddDouble(dsMapIndex, "status", 1.0 );
							RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
						}
					});

				} else {
					int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
					RunnerJNILib.DsMapAddString(dsMapIndex, "id", "android_rate_us" );
					RunnerJNILib.DsMapAddDouble(dsMapIndex, "status", 0.0 );
					RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
				}
			}
		});
		/*Task<ReviewInfo> request = manager.requestReviewFlow();
		request.addOnCompleteListener(task -> {
			if (task.isSuccessful()) {
				ReviewInfo reviewInfo = task.getResult();
				Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
				flow.addOnCompleteListener(task -> {
					int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
					RunnerJNILib.DsMapAddString(dsMapIndex, "id", "android_rate_us" );
					RunnerJNILib.DsMapAddDouble(dsMapIndex, "status", 1.0 );
					RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
				});
			} else {
				int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
				RunnerJNILib.DsMapAddString(dsMapIndex, "id", "android_rate_us" );
				RunnerJNILib.DsMapAddDouble(dsMapIndex, "status", 0.0 );
				RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
			}
		});*/
	}

}
