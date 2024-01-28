package com.kliachenko.quizgame.game

import android.content.Context
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View

class ActionButton : androidx.appcompat.widget.AppCompatButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        freezesText = true
        setBackgroundColor(Color.parseColor("#6AD9E8"))
    }

    fun updateState(newState: Int) {
        visibility = newState
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val state = ActionButtonSaveState(it)
            state.save(visibility)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as ActionButtonSaveState
        super.onRestoreInstanceState(restoredState.superState)
        updateState(restoredState.restore())
    }
}

class ActionButtonSaveState : View.BaseSavedState {

    private var state: Int = 0

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        parcelIn.readInt()
    }
    fun save(data: Int) {
        state = data
    }
    fun restore() = state

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(state)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ActionButtonSaveState> {
        override fun createFromParcel(parcel: Parcel): ActionButtonSaveState =
            ActionButtonSaveState(parcel)

        override fun newArray(size: Int): Array<ActionButtonSaveState?> =
            arrayOfNulls(size)
    }
}