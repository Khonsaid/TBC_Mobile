package uz.gita.latizx.entity.utils

import android.util.Log
import com.google.gson.Gson
import retrofit2.Response
import uz.gita.latizx.entity.retrofit.response.ErrorResponse

inline fun <T> handleResponse(response: Response<T>, onSuccess: (T) -> Unit = {}): Result<T> {
    val gson: Gson = Gson()

    return if (response.isSuccessful && response.body() != null) {
        Log.d("TTT", "handleResponse if: ${response.body()}")
        response.body()?.let { onSuccess(it) }
        Result.success(response.body()!!)
    } else if (response.errorBody() != null) {
        val errorBod = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
        Log.d("TTT", "handleResponse else if: ${errorBod.message}")
        Result.failure(Throwable(errorBod.message))
    } else Result.failure(Throwable("Unknown error"))
}

