<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h2 style="text-align: center">Личный кабинет</h2>
    <hr>
    <div style="text-align: center">
        <img src="/static/images/avatar.png"
             style="border:2px solid black; border-radius: 20px" height="120">
        <br>
        <h2>${user.name}</h2>
        <br>
        <h4>Логин: <b>${user.login}</b></h4>
        <br>
        <h4>Адрес: <b>${user.address}</b></h4>
        <hr>
        <#if user.isAdmin()>
            <a class="btn btn-dark mb-3" style="text-align: center; width: 50%"
               href="/admin">
                Панель администратора
            </a>
            <a class="btn btn-danger mb-2" style="text-align: center; width: 50%"
               href="/allusersrecords">
                Последние показания счетчиков
            </a>

            <a class="btn btn-danger mb-2" style="text-align: center; width: 50%"
               href="/allusersrequests">
                Последние заявки на ремонт
            </a>
        </#if>
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 50%"
           data-toggle="collapse" href="#collapseExample" role="button"
           aria-expanded="true" aria-controls="collapseExample">
            Добавить показания счетчиков
        </a>
        <div class="collapse mb-3" id="collapseExample" style="margin: 0 auto; text-align: center; width: 50%">
            <form action="/record/create" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="kitchenCold">Кухня: холодная вода</label>
                    <input type="number" required class="form-control"
                           id="kitchenCold" name="kitchenCold">
                </div>
                <div class="form-group">
                    <label for="kitchenHot">Кухня: горячая вода</label>
                    <div class="input-group mb-3">
                        <input type="number" required class="form-control" id="kitchenHot" name="kitchenHot">
                    </div>
                </div>

                <div class="form-group">
                    <label for="toiletCold">Санузел: холодная вода</label>
                    <div class="input-group mb-3">
                        <input type="number" required class="form-control" id="toiletCold" name="toiletCold">
                    </div>
                </div>

                <div class="form-group">
                    <label for="toiletHot">Санузел: горячая вода</label>
                    <div class="input-group mb-3">
                        <input type="number" required class="form-control" id="toiletHot" name="toiletHot">
                    </div>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <button type="submit" style="width: 100%" class="btn btn-dark">Добавить</button>
            </form>
        </div>
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 50%"
           data-toggle="collapse" href="#collapseExample2" role="button"
           aria-expanded="true" aria-controls="collapseExample">
            Добавить заявку на ремонт
        </a>
        <div class="collapse mb-3" id="collapseExample2" style="margin: 0 auto; text-align: center; width: 50%">
            <form action="/request/create" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="description">Описание</label>
                    <input type="text" required class="form-control"
                           id="description" name="description">
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file1" id="file1">
                        <label class="custom-file-label" for="file1">Первая фотография</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file2" id="file2">
                        <label class="custom-file-label" for="file2">Вторая фотография</label>
                    </div>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <button type="submit" style="width: 50%" class="btn btn-dark">Добавить</button>
            </form>
        </div>
        <a class="btn btn-dark mb-3" style="text-align: center; width: 50%"
           href="/my/records">
            История показаний счетчиков
        </a>
        <a class="btn btn-dark mb-3" style="text-align: center; width: 50%"
           href="/my/requests">
            История заявок на ремонт
        </a>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="text-align: center; width: 50%" class="btn btn-danger mb-2">
                Выйти из аккаунта
            </button>
        </form>
    </div>
</@t.template>