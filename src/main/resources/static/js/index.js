const dataTableBody = document.getElementById('data-table-body');
const reloadButton = document.getElementById('reload-button');
const pageInput = document.getElementById('page-input');
const sizeInput = document.getElementById('size-input');
const prevButton = document.getElementById('prev-button');
const nextButton = document.getElementById('next-button');
const loading = document.getElementById('loading');
const backToTop = document.getElementById('back-to-top');
const tableContainer = document.getElementById('table-container');

loadConfig(defaultConfig);
loadConfig(baseTheme);
loadConfig(lightTheme);

(function assignEvents() {
    // Reload button
    reloadButton.addEventListener('click', reload);

    // Back to top
    backToTop.addEventListener('click', function () {
        tableContainer.scrollIntoView();
    });

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

function loadData(parsedJSON, page, size) {
    let i = page * size;
    for (const e of parsedJSON) {
        // Replace falsy fields by empty string
        for (const field in e)
            if (!e[field])
                e[field] = '';

        i++;
        let tdList = '<td class="center">' + i + '</td>';

        tdList += '<td class="center">' + e['studentId'] + '</td>';
        tdList += '<td>' + e['fullName'] + '</td>';
        tdList += '<td class="center">' + e['classId'] + '</td>';
        tdList += '<td class="center">' + e['gender'] + '</td>';
        tdList += '<td class="center">' + e['birthday'] + '</td>';
        tdList += '<td class="center">' + e['placeOfBirth'] + '</td>';
        tdList += '<td class="center">' + e['ethnic'] + '</td>';
        tdList += '<td class="center">' + e['nationality'] + '</td>';

        tdList += '<td class="img">' +
            '<img src="' + e['image'] + '" onerror="this.style.display=\'none\'">' +
            '</td>';
        tdList += '<td class="img">' +
            '<img src="' + e['avatar'] + '" onerror="this.style.display=\'none\'">' +
            '</td>';

        tdList += '<td class="center">' +
            '<a href="tel:+84' + e['phone'] + '">' + e['phone'] +
            '</a></td>';
        tdList += '<td>' +
            '<a href="mailto:' + e['email'] + '">' + e['email'] +
            '</a></td>';
        tdList += '<td>' + e['facebook'] + '</td>';

        dataTableBody.innerHTML += '<tr>' + tdList + '</tr>';
    }
}

function reload() {
    // Clear table
    loading.classList.add('spinner');
    clearTableBody();

    // Build query
    const page = pageInput.value;
    const size = sizeInput.value;
    const query = buildQuery();

    // Fetch data
    let failed = false;
    fetch('/students/?page=' + page + '&size=' + size, {
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
            loadData(parsedJSON, page, size);
        })
        .catch(error => {
            console.log('Error', error);
        })
        .finally(() => {
            loading.classList.remove('spinner');
        });
}
