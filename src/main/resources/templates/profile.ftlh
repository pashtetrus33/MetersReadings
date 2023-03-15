<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h3 style="text-align: center">Личный кабинет</h3>
    <hr>
    <div style="text-align: center">
        <img src="/static/images/avatar.jpg"
             style="border:2px solid black; border-radius: 20px" height="110">
        <br>
        <h4><b>${user.name}</b></h4>
        <h4>${user.address}</h4>
        <h4>${user.email}</h4>

        <hr>
        <#if user.isAdmin()>
            <a class="btn btn-danger mb-2" style="text-align: center; width: 50%"
               href="/admin">
                Админка
            </a>
            <a class="btn btn-danger mb-2" style="text-align: center; width: 50%"
               href="/allusersrecords">
                Счетчики
            </a>

            <a class="btn btn-danger mb-2" style="text-align: center; width: 50%"
               href="/allusersrequests">
                Заявки
            </a>
        </#if>
        <#if user.isSupervisor()>
            <a class="btn btn-danger mb-2" style="text-align: center; width: 50%"
               href="/allusersrecords">
                Счетчики
            </a>

            <a class="btn btn-danger mb-2" style="text-align: center; width: 50%"
               href="/allusersrequests">
                Заявки
            </a>
        </#if>
        <#if user.isBuh()>
            <a class="btn btn-danger mb-2" style="text-align: center; width: 50%"
               href="/allusersrecords">
                Счетчики
            </a>
        </#if>
        <#if successmessage??>
            <h6 style="color: red">${successmessage}</h6>
        </#if>
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 50%"
           data-toggle="collapse" href="#collapseExample" role="button"
           aria-expanded="true" aria-controls="collapseExample">
            Добавить показания
        </a>
        <div class="collapse mb-3" id="collapseExample" style="margin: 0 auto; text-align: center">
            <form action="/record/create" method="post" enctype="multipart/form-data" style="width: 53%; margin: auto">
                <#if !user.isUserWithReducedData()>
                    <div class="form-group">
                        <label for="kitchenCold">Холодная вода (кухня)</label>
                        <input type="number" required class="form-control"
                               id="kitchenCold" name="kitchenCold">
                    </div>
                    <div class="form-group">
                        <label for="kitchenHot">Горячая вода (кухня)</label>
                        <div class="input-group mb-3">
                            <input type="number" required class="form-control" id="kitchenHot" name="kitchenHot">
                        </div>
                    </div>
                </#if>
                <div class="form-group">
                    <label for="toiletCold">Холодная вода (ванная)</label>
                    <div class="input-group mb-3">
                        <input type="number" required class="form-control" id="toiletCold" name="toiletCold">
                    </div>
                </div>

                <div class="form-group">
                    <label for="toiletHot">Горячая вода (ванная)</label>
                    <div class="input-group mb-3">
                        <input type="number" required class="form-control" id="toiletHot" name="toiletHot">
                    </div>
                </div>
                <#if user.isUserWithSpecialAddress()>
                    <div class="form-group">
                        <label for="neighborCold">Сосед: холодная вода</label>
                        <input type="number" required class="form-control"
                               id="neighborCold" name="neighborCold" placeholder="Спасибо от соседа :)">
                    </div>
                    <div class="form-group">
                        <label for="neighborHot">Сосед: горячая вода</label>
                        <input type="number" required class="form-control"
                               id="neighborHot" name="neighborHot" placeholder="Спасибо от соседа :)">
                    </div>
                </#if>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <button type="submit" style="width: 100%" class="btn btn-dark">Добавить</button>
            </form>
        </div>
        <a class="btn btn-dark mb-3" style="text-align: center; width: 50%"
           data-toggle="collapse" href="#collapseExample2" role="button"
           aria-expanded="true" aria-controls="collapseExample">
            Создать заявку
        </a>
        <div class="collapse mb-3" id="collapseExample2" style="margin: 0 auto; text-align: center; width: 50%">
            <form action="/request/create" method="post" enctype="multipart/form-data">
                <div class="form-outline">
                    <label for="description" style="margin: auto;">Описание</label>
                    <textarea class="form-control" id="description" rows="4" name="description"></textarea>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <button type="submit" class="btn btn-dark" style="margin: 5px">Добавить</button>
            </form>
        </div>
        <a class="btn btn-dark mb-3" style="text-align: center; width: 50%"
           href="/my/records">
            История показаний
        </a>
        <a class="btn btn-dark mb-3" style="text-align: center; width: 50%"
           href="/my/requests">
            История заявок
        </a>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="text-align: center; width: 50%" class="btn btn-danger mb-2">
                Выйти из аккаунта
            </button>
        </form>
    </div>
</@t.template>