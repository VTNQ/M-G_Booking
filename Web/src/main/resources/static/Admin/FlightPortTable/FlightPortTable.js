document.addEventListener("DOMContentLoaded", function () {
    var departureTimeInput = document.getElementById("datetimepicker");


    var dateValue = departureTimeInput.value.replace('Z', '');
    flatpickr("#datetimepicker", {
        enableTime: true,
        dateFormat: "Y-m-d\\TH:i",  // Date and time format without UTC 'Z'
        altInput: true,               // Use alternate input for user-friendly display
        altFormat: "F j, Y, H:i",     // User-friendly display format (e.g., "November 28, 2024, 19:00")
        time_24hr: true,              // 24-hour format
        defaultDate: dateValue,  // Default local time (e.g., HCM time)
        timezone: "Asia/Ho_Chi_Minh", // Set the time zone to HCM
    });
});


document.addEventListener("DOMContentLoaded", function () {
    var departureTimeInput = document.getElementById("Departure_Time");


    var dateValue = departureTimeInput.value.replace('Z', '');
    flatpickr("#Departure_Time", {
        enableTime: true,
        dateFormat: "Y-m-d\\TH:i",  // Date and time format without UTC 'Z'
        altInput: true,               // Use alternate input for user-friendly display
        altFormat: "F j, Y, H:i",     // User-friendly display format (e.g., "November 28, 2024, 19:00")
        time_24hr: true,              // 24-hour format
        defaultDate: dateValue,  // Default local time (e.g., HCM time)
        timezone: "Asia/Ho_Chi_Minh", // Set the time zone to HCM
    });
});



console.log(document.getElementById("Departure_Time").value)
let detailIndex = 0; // Tracks the number of detail flights
const detailFlights = []; // Array to store detail flights locally
const detailFlightsContainer = document.getElementById("detailFlights");
const addDetailFlightButton = document.getElementById("addDetailFlight");

// Function to render a detail flight input template
function renderDetailFlight(index, type = "", price = 0, quantity = 0) {
    const detailFlightTemplate = `
        <div class="detail-flight mb-3" id="detailFlight-${index}">
            <div class="row">
                <div class="col-md-4">
                    <label for="detailFlights[${index}].type" class="form-label">Type</label>
                    <select class="form-control" name="detailFlights[${index}].type" required>
                        <option value="Popular" ${type === "Popular" ? "selected" : ""}>Popular</option>
                        <option value="Premium Economy" ${type === "Premium Economy" ? "selected" : ""}>Premium Economy</option>
                        <option value="Merchant" ${type === "Merchant" ? "selected" : ""}>Merchant</option>
                        <option value="First Class" ${type === "First Class" ? "selected" : ""}>First Class</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label for="detailFlights[${index}].price" class="form-label">Price</label>
                    <input type="number" step="0.01" class="form-control" name="detailFlights[${index}].price" value="${price}" placeholder="Enter Price" required>
                </div>
                <div class="col-md-4">
                    <label for="detailFlights[${index}].quantity" class="form-label">Quantity</label>
                    <input type="number" class="form-control" name="detailFlights[${index}].quantity" value="${quantity}" placeholder="Enter Quantity" required>
                </div>
            </div>
            <div class="text-end mt-2">
                <button type="button" class="btn btn-danger btn-sm removeDetailFlight" data-index="${index}">Remove</button>
            </div>
        </div>
    `;
    detailFlightsContainer.insertAdjacentHTML("beforeend", detailFlightTemplate);

    // Add a blank entry to the detailFlights array
    detailFlights.push({ index, type, price, quantity });

    // Listen for changes in inputs
    const detailFlightElement = document.getElementById(`detailFlight-${index}`);
    detailFlightElement.querySelectorAll("input").forEach(input => {
        input.addEventListener("input", () => {
            const field = input.name.split(".")[1]; // Extract the field name (type, price, quantity)
            const flightDetail = detailFlights.find(df => df.index === index);
            if (flightDetail) {
                flightDetail[field] = input.value;
            }
        });
    });

    // Add event listener to the Remove button
    document.querySelector(`#detailFlight-${index} .removeDetailFlight`).addEventListener("click", (e) => {
        const detailFlightId = e.target.getAttribute("data-index");
        document.getElementById(`detailFlight-${detailFlightId}`).remove();

        // Remove from detailFlights array
        const indexToRemove = detailFlights.findIndex(df => df.index == detailFlightId);
        if (indexToRemove > -1) {
            detailFlights.splice(indexToRemove, 1);
        }
    });
}

// Add the first (default) detail flight entry on page load
renderDetailFlight(detailIndex, "", 0, 0);  // Default values
detailIndex++;  // Increment after initial entry

// Event listener for adding additional flights
addDetailFlightButton.addEventListener("click", () => {
    renderDetailFlight(detailIndex);
    detailIndex++;
});

let pageSize = 10;
let CurrentPage = 0;
let TotalPages = 10;
let SearchQuery="";
function updatePaginationControls(totalCount) {
    totalPages = Math.ceil(totalCount / pageSize);
    $('#prevPage').prop('disabled', CurrentPage === 1);
    $('#nextPage').prop('disabled', CurrentPage === totalPages);
    let pageNumbersHtml = '';
    for (let i = 0; i < totalPages; i++) {
        pageNumbersHtml += `<li class="page-item ${i === CurrentPage ? 'active' : ''}">
            <span class="page-link" onclick="changePage(${i})">${i+1}</span>
        </li>`;
    }
    $('#pageNumbers').html(pageNumbersHtml);
}

$('#prevPageItem').click(function () {
    if (CurrentPage >= 0) {
        CurrentPage--;

        fetchFlight(CurrentPage, pageSize,SearchQuery);
    }

    // Disable the button if CurrentPage is 0 or less
    if (CurrentPage <= 0) {
        $('#prevPageItem').prop('disabled', true); // Disable the button
    } else {
        $('#prevPageItem').prop('disabled', false); // Enable the button
    }
});
$('#nextPage').click(function () {
    if (CurrentPage < TotalPages) {
        CurrentPage++;

        fetchFlight(CurrentPage,pageSize,SearchQuery);
    }
})

function changePage(page) {

    if (page >= 0 && page <= totalPages) {
        CurrentPage = page;
        fetchFlight(CurrentPage , pageSize,SearchQuery);
    }
}


function fetchFlight(page, size,query) {
    const token = document.getElementById('token') ? document.getElementById('token').textContent : null;
    const id = document.getElementById('IdCountry') ? document.getElementById('IdCountry').textContent : null;
    if (!token) {
        console.error('No access token found.');
        return;
    }
    const url = `http://localhost:8686/Flight/FindAll/${id}?page=${page}&size=${size}&name=${query}`;
    $.ajax({
        url: url, method: 'GET', headers: {
            'Authorization': `Bearer ${token}`
        }, success: function (response) {
            const flight = response.content;
            const totalCount = response.totalElements;
            updatePaginationControls(totalCount);
            $('#FlightTableBody').empty();
            flight.forEach((flights, index) => {
                $('#FlightTableBody').append(`<tr>
                <td>${index + 1 + page * size}</td>
                <td>${flights.nameAirline}</td>
                <td>${flights.departure_airport}</td>
                <td>${flights.arrival_airport}</td>
                 <td>
                    <a href="/Admin/Flight/Edit/${flights.id}"  style="background-color: #4299e1;border-color: #4299e1" class="btn btn-info"><i style="color: white" class="fa fa-pencil"></i></a>
             
                    </td>
            </tr>`)
            })
        },
        error: function (xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    })

}
function SearchFlight(){
    SearchQuery=document.getElementById("searchFlight").value;
    CurrentPage=0;
    fetchFlight(CurrentPage,pageSize,SearchQuery)
}
fetchFlight(CurrentPage,pageSize,SearchQuery);