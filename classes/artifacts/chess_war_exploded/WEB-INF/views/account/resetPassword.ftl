[@override name="title"]账号管理 - 账号密码重置[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/account/pagination">账号管理</a></li>
    <li>账号密码重置</li>
</ul>
[/@override]

[@override name="headerText"]
账号密码 重置
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-8">
        <form class="form-horizontal" action="/account/reset_password" method="post" data-parsley-validate>

            <input type="hidden" name="id" value="${account.id!account.id}"/>
            <input type="hidden" name="version" value="${account.version!account.version}"/>

            <div class="form-group">
                <label for="name" class="col-md-3 control-label">账号名*</label>
                <div class="col-md-9">
                    <input type="text" class="form-control" value="${account.userName!}"  disabled/>
                </div>
            </div>

            [@spring.bind "command.password"/]
            <div class="form-group">
                <label for="projectName" class="col-md-3 control-label">密码*</label>
                <div class="col-md-9">
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="输入密码"
                           data-parsley-required="true" data-parsley-required-messages="密码不能为空"
                           data-parsley-minlength="6" data-parsley-trigger="change"/>
                    [@spring.showErrors "password" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.confirmPassword"/]
            <div class="form-group">
                <label for="description" class="col-md-3 control-label">确认密码*</label>
                <div class="col-md-9">
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                           placeholder="输入确认密码"
                           data-parsley-required="true" data-parsley-required-messages="确认密码不能为空"
                           data-parsley-equalto="#password" data-parsley-equalto-messages="两次密码输入不一致"
                           data-parsley-minlength="6" data-parsley-trigger="change"/>
                    [@spring.showErrors "confirmPassword" "parsley-required"/]
                </div>
            </div>

            <div class="text-center m-top-md">
                <button type="submit" class="btn btn-success">重置密码</button>
            </div>
        </form>
    </div>
    <div class="col-lg-3">
        <ul class="blog-sidebar-list font-18">创建注意事项
            <li>*位必填项</li>
        </ul>
    </div>
</div>

[/@override]

[@override name="bottomResources"]
    [@super /]
[/@override]
[@extends name="/decorator.ftl"/]