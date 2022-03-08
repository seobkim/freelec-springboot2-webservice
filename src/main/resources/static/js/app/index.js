//object 형식
var main = {
    init : function(){
        var _this = this;

        //글 작성 완료 버튼
        $('#btn-save').on('click',function(){
            _this.save();
        })

        //글 수정 완료 버튼
        $('#btn-update').on('click',function(){
            _this.update();
        })
    },
    save: function (){
        var data={
            title: $('#title').val(),
            author: $('#author').val(),
            content:$('#content').val()
        };

        console.log(data);
        $.ajax({
            type: 'POST',
            url:'/api/v1/posts',
            dataType:'json',
            contentType:'application/json; charset:utf-8',
            data:JSON.stringify(data)
        }).done(function(){
            alert("글이 등록되었습니다.");
            window.location.href="/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    update : function(){

    }
}

main.init();
