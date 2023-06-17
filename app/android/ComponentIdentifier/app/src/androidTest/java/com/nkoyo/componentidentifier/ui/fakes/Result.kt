package com.nkoyo.componentidentifier.ui.fakes

import android.graphics.Bitmap
import com.nkoyo.componentidentifier.ui.viewmodel.HighestProbabilityComponent


fun produceResult(bitmap: Bitmap?):  HighestProbabilityComponent {
    return HighestProbabilityComponent.Default
}