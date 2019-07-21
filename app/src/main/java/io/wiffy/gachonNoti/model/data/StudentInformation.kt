package io.wiffy.gachonNoti.model.data

data class StudentInformation(val name:String, val number:String, val id:String, val password:String,val department:String,val imageURL:String)
{
    override fun toString(): String {
        return "name:$name\nnumber:$number\ndepartment:$department"
    }

}