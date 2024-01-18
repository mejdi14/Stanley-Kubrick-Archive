package com.example.stanley_kubrick_archive.utils

 fun cardAnimationPosition(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}