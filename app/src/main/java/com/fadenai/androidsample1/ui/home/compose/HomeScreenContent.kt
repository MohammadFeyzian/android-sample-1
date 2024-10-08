package com.fadenai.androidsample1.ui.home.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fadenai.androidsample1.R
import com.fadenai.androidsample1.data.entity.CourseEntity
import com.fadenai.androidsample1.data.mock.mockCategorizedCourses
import com.fadenai.androidsample1.ui.composecommon.RatingBar
import com.fadenai.androidsample1.ui.theme.AppTheme

@Composable
fun HomeScreenContent(
    courseList: Map<String, List<CourseEntity>>,
    onItemClicked: (id: Int) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        courseList.forEach { (category, courses) ->

            item {
                Text(
                    text = category,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            items(courses) { item ->
                ListItem(item, onItemClicked)
            }
        }


        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ListItem(
    course: CourseEntity,
    onItemClicked: (id: Int) -> Unit
) {

    Card(
        onClick = { onItemClicked.invoke(course.id) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            val (image, title, instructor, rating, duration) = createRefs()

            // Create an ImageRequest and disable caching for testing purpose
            val imageRequest = ImageRequest.Builder(LocalContext.current)
//                .data(course.img ?: R.drawable.ic_launcher_background)
                .data(course.img)
                .memoryCachePolicy(coil.request.CachePolicy.DISABLED)  // Disable memory cache
                .diskCachePolicy(coil.request.CachePolicy.DISABLED)    // Disable disk cache
                .build()

            AsyncImage(
                model = course.img,
                contentDescription = "Course Image",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop
            )

            Text(
                text = course.title,
//                text = "Long name Long name Long name Long name Long name ",
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(image.top)
                    start.linkTo(image.end)
                    bottom.linkTo(instructor.top)
                    end.linkTo(duration.start)
                    width = Dimension.fillToConstraints
                },
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = course.instructor,
//                text = "Long name Long name Long name ",
                modifier = Modifier.constrainAs(instructor) {
                    top.linkTo(title.bottom)
                    bottom.linkTo(image.bottom)
                    start.linkTo(image.end)
                    end.linkTo(rating.start)
                    width = Dimension.fillToConstraints
                },
                style = MaterialTheme.typography.bodyMedium
            )

            RatingBar(
                modifier = Modifier
                    .constrainAs(rating) {
                        top.linkTo(title.bottom)
                        end.linkTo(duration.start)
                    },
                rating = course.rating
            )

            Text(
                text = "${course.duration} hr",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(duration) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreenContent() {
    AppTheme {
        HomeScreenContent(courseList = mockCategorizedCourses) {}
    }
}