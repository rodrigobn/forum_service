package br.com.rodritodev.forum.mapper

interface Mapper<T, U> {

    fun map(t: T): U
}
