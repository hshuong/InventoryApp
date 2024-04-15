package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)
    // Vi kieu Item duoc dinh nghia bang Entity
    //    @Entity(tableName = "items")
    //    data class Item(

    //    The @Entity annotation marks a class as a database Entity class.
    //    For each Entity class, the app creates a database table to hold the items.
    //    Each field of the Entity is represented as a column in the database
    @Update
    suspend fun update(item: Item)
    @Delete
    suspend fun delete(item: Item)
    // a SQLite query to retrieve a particular item from the item table
    // based on the given id
    // Select all columns from the items, where the id matches the :id
    // Notice the :id uses the colon notation in the query to reference
    // arguments in the function => Trong ham getItem, tham so id
    // phai giong voi tham so :id trong cau SQL query
    //
    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>
    // Can dung Flow in the persistence layer.
    // With Flow as the return type, you receive notification
    // whenever the data in the database changes
    // Chi can khai bao Flow mot lan o day, Room se gui trang thai moi cho Flow biet
    // moi khi co database duoc thay doi. Dung de hien thi lai cac item
    // co data thay doi trong List cac item tren giao dien mot cach tu dong.
    // Mot dac diem nua: do ham getItem co kieu tra ve la 1 Flow, ma Flow chay
    // o coroutine nen, ham nay khong dat chu suspend vi mac dinh Flow da la
    // suspend.
    // Note: Flow in Room database can keep the data up-to-date
    // by emitting a notification whenever the data in the database changes.
    // This allows you to observe the data and update your UI accordingly.
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}