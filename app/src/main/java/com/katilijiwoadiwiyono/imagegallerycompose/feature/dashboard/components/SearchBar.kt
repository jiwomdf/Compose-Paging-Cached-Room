package com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.katilijiwoadiwiyono.imagegallerycompose.ui.theme.Red80
import com.katilijiwoadiwiyono.imagegallerycompose.ui.theme.md_theme_light_primary


@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        modifier = Modifier,
        search = "",
        onValueChange = {},
    )
}

@Composable
fun SearchBar(
    modifier: Modifier,
    search: String,
    onValueChange: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp))
            .border(border = BorderStroke(1.dp, md_theme_light_primary), shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp))
    ) {
        TextField(
            modifier = modifier,
            value = search,
            onValueChange = onValueChange,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                errorIndicatorColor = Red80,
                focusedIndicatorColor = Transparent,
                cursorColor = Red80
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = ""
                )
            },
            placeholder = { Text(text = "Search") },
        )
    }
}