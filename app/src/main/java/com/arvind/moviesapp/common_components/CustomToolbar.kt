package com.arvind.moviesapp.common_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arvind.moviesapp.ui.theme.ColorWhite

@Composable
fun CustomToolbar(title: String, onBack: () -> Unit) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .padding(start = 15.dp)
                    .clickable {
                        onBack()
                    }
                    .size(28.dp),
                tint = ColorWhite
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                fontSize = 20.sp,
                color = ColorWhite,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}