package com.example.inventory.ui.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Category
import com.example.inventory.data.Item
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme
import com.example.inventory.utilities.AutoSizeText

object ItemListDestination : NavigationDestination {
    override val route = "item_list"
    override val titleRes = R.string.item_list
    const val categoryIdArg = "categoryId"
    val routeWithArgs = "$route/{$categoryIdArg}"
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    viewModel: ItemListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val itemListUiState by viewModel.itemListUiState.collectAsState()

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->
        ItemListBody(
            itemList = itemListUiState.itemList,//listOf(),
            onItemClick = navigateToItemUpdate,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ItemListBody(
    itemList: List<Item>, onItemClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (itemList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            InventoryCarouselGrid(
                getImageList(),
            //InventoryGrid(
            //InventoryList(
                itemList = itemList,
                onItemClick = { onItemClick(it.id) },
                // hoac viet thanh:
                // onItemClick = { item -> onItemClick(item.id) },
                // hoac viet thanh
                // onItemClick = onItemClick, voi kieu onItemClick: (Item) -> Unit
                // cho khop voi dinh nghia tham so o dinh nghia ham InventoryList
                //modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}


@Composable
private fun InventoryList(
    itemList: List<Item>, onItemClick: (Item) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = itemList, key = { it.id }) { item -> // neu dung it thi
            // dung xoa chu item -> va thay chu item o 02 noi la chu it
            InventoryItem(item = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) })
        }
    }
}

@Composable
private fun InventoryItem(
    item: Item, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = modifier) {
            Image(
                painter = painterResource(id = R.drawable.test_1),
                contentDescription = "hello",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .widthIn(120.dp)
                    .height(160.dp),
                alpha = 0.9F
                )
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = item.formatedPrice(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    text = stringResource(R.string.in_stock, item.quantity),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    InventoryTheme {
        ItemListBody(
            listOf(
                Item(1, "Game", 100.0, 20),
                Item(2, "Pen", 200.0, 30),
                Item(3, "TV", 7777.3, 888888),
                Item(4, "Game", 100.0, 20),
                Item(5, "Pen", 200.0, 30),
                Item(6, "TV", 300.0, 50),
        ), onItemClick = {})
    }
}
@Preview(showBackground = true)
@Composable
fun ItemListEmptyListPreview() {
    InventoryTheme {
        ItemListBody(listOf(), onItemClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryItemPreview() {
    InventoryTheme {
        com.example.inventory.ui.item.InventoryItem(
            Item(1, "Game", 100.0, 20),
        )
    }
}


@Composable
private fun InventoryGrid(
    itemList: List<Item>, onItemClick: (Item) -> Unit, modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
    //LazyVerticalStaggeredGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        //columns = GridCells.Fixed(2),
        //columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        //verticalItemSpacing = 16.dp,
        // padding vertical la keo dai tu trai sang phai
        // vertical de phan cach giua cac thanh phan greeting.
        verticalArrangement = Arrangement.spacedBy(16.dp), // duong ngang
        horizontalArrangement = Arrangement.spacedBy(16.dp), // duong doc phan cach
        modifier = modifier
    )
    //LazyColumn(modifier = modifier)
    {
        //itemList.forEachIndexed { index, item ->
        items(items = itemList, key = { it.id }) { item -> // neu dung it thi
            CategoryItem(item = item,
                    modifier = Modifier
                        //.padding(dimensionResource(id = R.dimen.padding_small))
                        .clickable { onItemClick(item) })
            }
//            if (index % 3 == 0 ) {
//                item(span = { GridItemSpan(maxLineSpan) }) {
//                    InventoryItem(item = item,
//                        modifier = Modifier
//                            .padding(dimensionResource(id = R.dimen.padding_small))
//                            .clickable { onItemClick(item) })
//                }
//            } else {
//                item(span = { GridItemSpan(1) }) {
//                    InventoryItem(item = item,
//                        modifier = Modifier
//                            .padding(dimensionResource(id = R.dimen.padding_small))
//                            .clickable { onItemClick(item) })
//                }
//            }

//            InventoryItem(item = item,
//                modifier = Modifier
//                    .padding(dimensionResource(id = R.dimen.padding_small))
//                    .clickable { onItemClick(item) })
       // }

        //}
    }
}
@Preview(showBackground = true)
@Composable
fun GridHomeBodyPreview() {
    InventoryTheme {
        InventoryGrid(listOf(
            Item(1, "Game", 100.0, 20),
            Item(2, "Pen", 200.0, 30), 
            Item(3, "TV", 7777.3, 888888),
            Item(4, "Game", 100.0, 20),
            Item(5, "Pen", 200.0, 30),
            Item(6, "TV", 300.0, 50),
        ), onItemClick = {})
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // padding vertical la keo dai tu trai sang phai
    // vertical de phan cach giua cac thanh phan greeting.
    // phan cach 4.dp o day thi 2 cai greeting gan nhau
    // se phan cach la 4 + 4 = 8 dp
    // De phan cach giua cac thanh phan la 8 va phan cach
    // giua canh tren, canh duoi man hinh voi thanh phan tren
    // duoi thi phai them phan cach padding vertical
    // cho compose boc lay cac thanh phan, la Column. Column phai
    // co them 1 padding vertical la 4 nua de khi ghep voi padding
    // vertical cua thanh phan dau la 4 thi bang 8 padding, tuong tu
    // voi padding vertical thanh phan duoi cung cung la 4 cua thanh
    // phan cuoi + 4 cua padding vertical duoi cua Column boc ngoai
    // la
    // padding horizontal la keo tu tren xuong duoi
    // de can bang tren duoi trai phai thi them padding horizontal
    // cua Surface la 8
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = modifier.padding(24.dp)) {
            Column(modifier = modifier.weight(1f)) {
                Text(text = "Hello ")
                Text(text = name)
            }
            ElevatedButton(
                //modifier = modifier.weight(2f),
                onClick = { /* TODO */ }
            ) {
                Text("Show more")
            }
        }

    }
}
@Composable
fun TestMyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    // padding vertical boc ngoai la de cach 2 phia tren, duoi
    // cua cac thanh phan greeting voi canh tren, canh duoi man hinh
    //
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    InventoryTheme {
        TestMyApp()
    }
}

@Composable
fun CategoryItem(item: Item, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(180.dp)
        //shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(
//            containerColor =
//            MaterialTheme.colorScheme.secondaryContainer,
//        )
    ) {
        Box() {
            Image(
                painter = painterResource(id = R.drawable.phong_canh),
                contentDescription = "hello",
                //modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                alpha = 0.9F
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
//                Text(
//                    text = item.name,
//                    //"Chua co bao gio dep nhu hom nay dat nuoc may troi long ta me say",
//                    //modifier = Modifier.align(alignment = Alignment.End),
//                    textAlign = TextAlign.Center,
//                    color = Color.White
//                )
                AutoSizeText(
                    //text = "Chua co bao gio dep nhu hom nay dat nuoc may troi long ta me say",
                    text = item.name,
                    //modifier.padding(16.dp),
                    minTextSize = 14.sp,
                    maxTextSize = 24.sp,
                    alignment = Alignment.Center,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,

                    color = Color.White,
                )
            }
        }
    }
}
@Preview(showBackground = true, widthDp = 168)
@Composable
fun CategoryItemPreview() {
    InventoryTheme {
        Surface(
            //color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(168.dp)
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            CategoryItem(Item(4, "Chua co bao gio dep nhu hom nay dat nuoc may troi long ta me say. Chua co bao gio dep nhu hom nay dat nuoc. Chua co bao gio dep nhu hom nay dat nuoc", 100.0, 20))
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(imageList: List<Int>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })
    Column(
        modifier = Modifier.fillMaxWidth()
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { currentPage ->
                Image(
                    painter = painterResource(id = imageList[currentPage]),
                    contentDescription = "post image",
                    modifier = Modifier
                        .fillMaxWidth()
//                        .blur(radiusX = 10.dp,
//                                radiusY = 10.dp,
//                                edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
//                            )


                    //.padding(start = 4.dp, end = 4.dp)
                    ,
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.8f))
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
            ) {
                Text("${pagerState.currentPage + 1}", color = Color.White)
                Text("/", color = Color.White)
                Text("${pagerState.pageCount}", color = Color.White)
            }

        }

        if (pagerState.pageCount > 1) {
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    //.align(Alignment.BottomCenter)
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(4.dp)
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ImageCarouselPreview() {
    InventoryTheme {
        Surface(
            //color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()

                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            ImageCarousel(imageList = getImageList())
        }

    }
}

@Composable
private fun InventoryCarouselGrid(
    imageList: List<Int>,
    itemList: List<Item>,
    onItemClick: (Item) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        //LazyVerticalStaggeredGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        //columns = GridCells.Fixed(2),
        //columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        //verticalItemSpacing = 16.dp,
        // padding vertical la keo dai tu trai sang phai
        // vertical de phan cach giua cac thanh phan greeting.
        verticalArrangement = Arrangement.spacedBy(12.dp), // duong ngang
        horizontalArrangement = Arrangement.spacedBy(12.dp), // duong doc phan cach
        modifier = modifier
    )
    //LazyColumn(modifier = modifier)
    {
        item (span = { GridItemSpan(maxLineSpan) }){
            ImageCarousel(imageList = imageList)
        }
        //itemList.forEachIndexed { index, item ->
        items(items = itemList, key = { it.id }) { item -> // neu dung it thi
            CategoryItem(item = item,
                modifier = Modifier
                    //.padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) })
        }
//            if (index % 3 == 0 ) {
//                item(span = { GridItemSpan(maxLineSpan) }) {
//                    InventoryItem(item = item,
//                        modifier = Modifier
//                            .padding(dimensionResource(id = R.dimen.padding_small))
//                            .clickable { onItemClick(item) })
//                }
//            } else {
//                item(span = { GridItemSpan(1) }) {
//                    InventoryItem(item = item,
//                        modifier = Modifier
//                            .padding(dimensionResource(id = R.dimen.padding_small))
//                            .clickable { onItemClick(item) })
//                }
//            }

//            InventoryItem(item = item,
//                modifier = Modifier
//                    .padding(dimensionResource(id = R.dimen.padding_small))
//                    .clickable { onItemClick(item) })
        // }

        //}
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryCarouselGridPreview() {
    InventoryTheme {
        InventoryCarouselGrid(
            getImageList(),
            listOf(
            Item(1, "Game", 100.0, 20),
            Item(2, "Pen", 200.0, 30),
            Item(3, "TV", 7777.3, 888888),
            Item(4, "Game", 100.0, 20),
            Item(5, "Pen", 200.0, 30),
            Item(6, "TV", 300.0, 50),
        ), onItemClick = {})
    }
}

fun getImageList(): List<Int> = listOf(
    R.drawable.post_image6small,
    R.drawable.post_image1,
    R.drawable.post_image2,
    R.drawable.post_image3,
    R.drawable.post_image4small,
    R.drawable.post_image5small,
)