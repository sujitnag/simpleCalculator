import android.util.Log
import com.sujitnag.simplecalculator.R
import java.lang.reflect.Type
import java.util.regex.Pattern

class MyMath constructor(inputs:String){
//val input=input
    var input=inputs.split(" ")
    var inputL= input.filter { it!=null && it!=""  }


    val precedenc= mapOf("-" to 1,"+" to 2,"/" to 3,"*" to 4)
    var operandL= mutableListOf<Double>()
    var operatorL= mutableListOf<String>()

    fun checkDNumber(str:String):Boolean{
        val pat="\\d*\\.?\\d*".toPattern()
        val matcher=pat.matcher(str)
        return matcher.find()
    }
    fun calculateValue(opd1:Double, opd2:Double,op:String):Double{
      return  when(op){
            "+"->opd1.plus(opd2)
            "-"->opd1.minus(opd2)
            "*"->opd1.times(opd2)
            "/"->opd1.div(opd2)
          else -> Double.MAX_VALUE
      }

    }

    private fun oparatorCheck(it:String){
        when(operatorL.isEmpty()){
            true->{operatorL.add(it)
                return
            }
            false->{
                var lop=   operatorL.last()
                when (lop){
                    "("-> {operatorL.add(it)
                            return}
                     else->{
                         var topp=precedenc.get(lop)
                         var scanop=precedenc.get(it)
                         if(topp!=null &&scanop!=null)
                             if((scanop.minus( topp))>=0){
                                 operatorL.add(it)
                                 return
                             }else{
                                 var opd2= operandL.removeLast()
                                 var opd1= operandL.removeLast()
                                 var op=operatorL.removeLast()
                                 var r=   calculateValue(opd1,opd2,op)
                                 operandL.add(r)
                                 oparatorCheck(it)
                             }
                     }
                }


            }
        }

    }

    fun inFixCal():String{

       // Log.d("Infix", operatorL.toString())
        //return inputL.toString()
         inputL.forEach {

            when(it) {
                in precedenc.keys -> {
                    oparatorCheck(it)
                }
                "(" -> {
                    operatorL.add("(")
                    Log.d("Infix (", operatorL.toString())
                }
                ")" -> {

                    loop@ while (operatorL.isNotEmpty()) {
                        var op1 = operatorL.removeLast()
                        when (op1) {
                            "(" -> {
                               // Log.d("Infix )(", operatorL.toString())
                                break@loop
                            }
                            else -> {
                                Log.d("Infix )", operatorL.toString())
                                var opd2 = operandL.removeLast()
                                var opd1 = operandL.removeLast()
                                operandL.add(calculateValue(opd1, opd2, op1))
                            }
                        }
                    }

                }
                else -> {
                    if (checkDNumber(it)) {
                        Log.d("Infix-else", it.toString())
                        operandL!!.add(it.toDouble())
                    } else {
                        return "Error"
                    }
                    //  Log.d("Infix-else", operandL.toString())
                }
            }
        }
        while (operatorL.isNotEmpty()){
            var op1=operatorL.removeLast()
            var opd2=operandL.removeLast()
            var opd1=operandL.removeLast()
            operandL.add(calculateValue(opd1, opd2,op1))
        }
        if(operatorL.size>1){
            return "someting wrong"+operandL.first().toString()
        }else{
            return operandL.first().toString()
        }




    }


  /*  fun calculate(input:String):Int {


    }*/

}