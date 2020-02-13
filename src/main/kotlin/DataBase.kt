interface DataBase {

    fun <T> saveData(data:T)

    fun <T> readData() : List<T>

}