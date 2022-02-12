import android.app.Activity
import android.widget.Toast

fun String.getReleaseYear(): String {
    return try {
        this.split("-").first()
    } catch (e: Exception) {
        ""
    }
}

fun Activity.showToast(toastText : String?){
    Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show()
}