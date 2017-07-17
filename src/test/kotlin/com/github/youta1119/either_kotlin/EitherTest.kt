package com.github.youta1119.either_kotlin

import com.taroid.knit.should
import org.hamcrest.CoreMatchers.*
import org.hamcrest.collection.IsEmptyIterable
import org.junit.Test


class EitherTest {
    @Test fun foldTest_Right() {
        Right<String, Int>(1).fold(
                left = {
                    assert(false)//testing not called
                },
                right = {
                    it.should.be(1)
                }
        )

        Left<String, Int>("a").fold(
                left = {
                    it.should.be("a")
                },
                right = {
                    assert(false)//testing not called
                }
        )
    }

    @Test fun swapTest() {
        Right<String, Int>(1).swap().should.be(instanceOf(Left::class.java))
        Left<String, Int>("a").swap().should.be(instanceOf(Right::class.java))
    }

    @Test fun joinRightTest() {
        val right_right = Right<String, Either<String, Int>>(Right(12)).joinRight()
        right_right.should.be(instanceOf(Right::class.java))
        (right_right as Right).value.should.be(12)

        val right_left = Right<String, Either<String, Int>>(Left("flower")).joinRight()
        right_left.should.be(instanceOf(Left::class.java))
        (right_left as Left).value.should.be("flower")

        val left_left = Left<String, Either<String, Int>>("flower").joinRight()
        left_left.should.be(instanceOf(Left::class.java))
        (left_left as Left).value.should.be("flower")
    }

    @Test fun joinLeftTest() {
        val left_right = Left<Either<Int,String>,String>(Right("flower")).joinLeft()
        left_right.should.be(instanceOf(Right::class.java))
        (left_right as Right).value.should.be("flower")

        val right_left = Left<Either<Int,String>,String>(Left(12)).joinLeft()
        right_left.should.be(instanceOf(Left::class.java))
        (right_left as Left).value.should.be(12)

        val left_left = Right<Either<Int,String>,String>("flower").joinLeft()
        left_left.should.be(instanceOf(Right::class.java))
        (left_left as Right).value.should.be("flower")
    }

    @Test fun forEachTest(){
         Right<String, Int>(1).foreach {
             it.should.be(1)
         }

         Left<String, Int>("a").foreach {
             assert(true) //testing not called
         }
    }

    @Test fun getOrElseTest(){
        Right<String, Int>(12).getOrElse(17).should.be(12)
        Left<Int,String>(12).getOrElse(17).should.be(17)
    }

    @Test fun containsTest(){
        Right<Int,String>("something").contains("something").should.be(true)
        Right<Int,String>("anything").contains("something").should.be(false)
        Left<Int,String>(12).contains("something").should.be(false)
    }

    @Test fun forAllTest(){
        Right<Int,Int>(12).forall { it > 10 }.should.be(true)
        Right<Int,Int>(7).forall { it > 10 }.should.be(false)
        Left<Int,Int>(12).forall { it > 10 }.should.be(false)
    }
    @Test fun flatMapTest(){
        val right_flatmap_result = Right<String,Int>(12).flatMap {Right<String,String>("flower") }
        right_flatmap_result.should.be(instanceOf(Right::class.java))
        (right_flatmap_result as Right).value.should.be("flower")

        val left_flatmap_result = Left<Int,String>(12).flatMap { Right<Int,String>("flower") }
        left_flatmap_result.should.be(instanceOf(Left::class.java))
        (left_flatmap_result as Left).value.should.be(12)
    }

    @Test fun mapTest(){
        val right_map_result = Right<String,Int>(12).map { "flower" }
        right_map_result.should.be(instanceOf(Right::class.java))
        (right_map_result as Right).value.should.be("flower")

        val left_map_result = Left<Int,String>(12).map { "flower" }
        left_map_result.should.be(instanceOf(Left::class.java))
        (left_map_result as Left).value.should.be(12)
    }

    @Test
    fun filterOrElseTest(){
        val right_filter_true_result = Right<Int,Int>(12).filterOrElse({it > 10},-1)
        right_filter_true_result.should.be(instanceOf(Right::class.java))
        (right_filter_true_result as Right).value.should.be(12)

        val right_filter_false_result = Right<Int,Int>(7).filterOrElse({it > 10},-1)
        right_filter_false_result.should.be(instanceOf(Left::class.java))
        (right_filter_false_result as Left).value.should.be(-1)

        val left_filter_result = Left<Int,Int>(7).filterOrElse({it > 10},-1)
        left_filter_result.should.be(instanceOf(Left::class.java))
        (left_filter_result as Left).value.should.be(7)
    }

    @Test fun toSeqTest(){
        Right<String,Int>(12).toSeq().asIterable().should.be(hasItem(12))
        Left<String,Int>("test").toSeq().asIterable().should.be(IsEmptyIterable<Int>())
    }

    @Test fun toListTest(){
        Right<String,Int>(12).toList().asIterable().should.be(hasItem(12))
        Left<String,Int>("test").toList().asIterable().should.be(IsEmptyIterable<Int>())
    }
}
