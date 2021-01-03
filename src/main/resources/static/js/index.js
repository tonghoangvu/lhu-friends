const dataTableBody = document.getElementById('data-table-body');
const reloadButton = document.getElementById('reload-button');
const pageInput = document.getElementById('page-input');
const sizeInput = document.getElementById('size-input');
const prevButton = document.getElementById('prev-button');
const nextButton = document.getElementById('next-button');

loadConfig(defaultConfig);
loadConfig(baseTheme);
loadConfig(lightTheme);

(function assignEvents() {
    // Reload button
    reloadButton.addEventListener('click', reload);

    // Previous
    prevButton.addEventListener('click', function () {
        if (pageInput.value == 0)
            return;
        pageInput.value--;
        reload();
    });

    // Next
    nextButton.addEventListener('click', function () {
        pageInput.value++;
        reload();
    });

    // Filter cells, page and size input
    const filterCells = document.querySelectorAll(
        'td[contenteditable="true"], #page-input, #size-input');
    for (let i = 0; i < filterCells.length; i++)
        filterCells[i].addEventListener('keypress', function (event) {
            if(event.keyCode == 13) {
                event.preventDefault();
                reload();
            }
        });
})();

function clearTableBody() {
    dataTableBody.innerHTML = '';
}

function buildQuery() {
    const query = {};
    const QUERY_FIELDS = [
        'studentId', 'fullName', 'classId', 'gender',
        'birthday', 'placeOfBirth', 'ethnic', 'nationality',
        'phone', 'email', 'facebook'
    ];
    for (const field of QUERY_FIELDS)
        if (document.getElementById(field).innerText)
            query[field] = document.getElementById(field).innerText;
    return query;
}

function loadData(parsedJSON) {
    for (const e of parsedJSON) {
        let tdList = '';
        const FIELDS = [
            'studentId', 'fullName', 'classId', 'gender', 'birthday',
            'placeOfBirth', 'ethnic', 'nationality', 'phone', 'email',
            'facebook'
        ];
        for (let field of FIELDS) {
            if (!e[field])
                e[field] = '';
            tdList += '<td>' + e[field] + '</td>';
        }

        tdList += '<td><img src="' + e['image'] + '" width="100"></td>';
        tdList += '<td><img src="' + e['avatar'] + '" width="100"></td>';

        dataTableBody.innerHTML += '<tr>' + tdList + '</tr>';
    }
}

function reload() {
    // Clear table
    clearTableBody();

    // Build query
    const query = buildQuery();

    // Fetch data
    let failed = false;
    fetch('/students/?page=' + pageInput.value + '&size=' + sizeInput.value, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(query)
    })
        .then(response => {
            failed = !response.ok;
            return response.json();
        })
        .then(parsedJSON => {
            if (failed)
                return alert(parsedJSON.code + ': ' + parsedJSON.message);
            loadData(parsedJSON);
        })
        .catch(error => {
            console.log('Error', error);
        });
}
