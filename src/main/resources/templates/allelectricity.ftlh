<#import "blocks/template.ftlh" as t>
<@t.template user>
    `<h4 style="text-align: center">Ввод показаний счетчиков электричества</h4>
    <hr>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark" style="margin: 10px;"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <br>
    <#if successmessage??>
        <h6 style="color: red">${successmessage}</h6>
    </#if>
    <table class="table table-hover table-dark">
        <tr>
            <th scope="col">Здание</th>
            <th scope="col">Квартира</th>
            <th scope="col">Показания</th>
            <th scope="col">Действие</th>
        </tr>
        <tr>
            <th>
                <form action="/allelectricity" method="post" style="margin: 0 auto; width: 60%">
                    <div class="form-group">
                        <select id="building" required name="building" class="form-control">
                            <option value="">Здание не выбрано</option>
                            <option value="Школьно-жилой дом">Школьно-жилой дом</option>
                            <option value="Жилой дом №1">Жилой дом №1</option>
                            <option value="Жилой дом №2">Жилой дом №2</option>
                        </select>
                    </div>
            </th>
            <th>
                <div class="form-group">
                    <select id="flat" required name="flat" class="form-control">
                        <option value="">Квартира не выбрана</option>
                        <option value="кв.№11">кв.№11</option>
                        <option value="кв.№21">кв.№21</option>
                        <option value="кв.№22">кв.№22</option>
                        <option value="кв.№23">кв.№23</option>
                        <option value="кв.№24">кв.№24</option>
                        <option value="кв.№25">кв.№25</option>
                        <option value="кв.№29">кв.№29</option>
                        <option value="кв.№30">кв.№30</option>
                        <option value="кв.№31">кв.№31</option>
                        <option value="кв.№32">кв.№32</option>
                        <option value="кв.№33">кв.№33</option>
                        <option value="кв.№34">кв.№34</option>
                        <option value="кв.№40">кв.№40</option>
                        <option value="кв.№41">кв.№41</option>
                        <option value="кв.№42">кв.№42</option>
                        <option value="кв.№43">кв.№43</option>
                        <option value="кв.№44">кв.№44</option>
                        <option value="кв.№51">кв.№51</option>
                        <option value="кв.№52">кв.№52</option>
                        <option value="кв.№53">кв.№53</option>
                        <option value="кв.№54">кв.№54</option>
                        <option value="кв.№60">кв.№60</option>
                        <option value="кв.№61">кв.№61</option>
                        <option value="кв.№62">кв.№62</option>
                    </select>
                </div>
            </th>
            <th>
                <div class="form-group">
                    <input type="number" class="form-control" required id="electricity" name="electricity">
                </div>
            </th>
            <th>
                <#if !successmessage??>
                    <input type="hidden" value="${_csrf.token}" name="_csrf">
                    <button type="submit" class="btn btn-danger mb-2">Сохранить</button>
                    </form>
                <#else>
                    <div style="margin: 0 auto; text-align: center">
                        <a class="btn btn-danger mb-2" href="/allelectricity" role="button">
                            Обновить
                        </a>
                    </div>
                </#if>
            </th>
        </tr>
    </table>
</@t.template>