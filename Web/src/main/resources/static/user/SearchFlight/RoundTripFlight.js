function initFlatpickr(selector, options) {
    // Khởi tạo flatpickr với selector và các tùy chọn
    flatpickr(selector, options);
}
document.getElementById('selectAllToggle').addEventListener('change',function (){
    const isChecked=this.checked;
    if(isChecked){
        selectedAirlines=[];
        document.querySelectorAll('.flight-select').forEach(function(checkbox) {
            const airlineId = checkbox.closest('.custom-control').querySelector('input').getAttribute('id').split('-')[1];
            selectedAirlines.push(airlineId);
            checkbox.checked = true;
        });
    }else{
        selectedAirlines = [];
        document.querySelectorAll('.flight-select').forEach(function(checkbox) {
            checkbox.checked = false;
        });
    }
    updateFlightVisibility();
})
document.querySelectorAll('.airline-checkbox').forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        const airlineId = this.id.split('-')[1];
        console.log(airlineId)
        if (this.checked) {
            selectedAirlines.push(airlineId); // Add airline ID to selected list
        } else {
            selectedAirlines = selectedAirlines.filter(function(id) {
                return id !== airlineId;
            });
        }

        updateFlightVisibility();

    });
});
document.addEventListener("DOMContentLoaded", function() {
    var priceRange = document.getElementById('price-range');
    var priceRangeText = document.getElementById('price-range-text');
    var minPrice = parseFloat(document.getElementById('minPrice').value) || 0;
    var maxPrice = parseFloat(document.getElementById('maxPrice').value) || 0;

    // Initialize the noUiSlider
    noUiSlider.create(priceRange, {
        start: [minPrice, maxPrice], // Set the default values for the range
        connect: true, // Connect the handles
        range: {
            'min': minPrice,    // Minimum value of the slider
            'max': maxPrice   // Maximum value of the slider
        },
        step: 10,  // Step size for the slider
        tooltips: true, // Show the current value when the user drags the handle
        format: {
            to: function (value) {
                return '£' + Math.round(value); // Format values as currency
            },
            from: function (value) {
                return value.replace('£', ''); // Convert from the formatted string
            }
        }
    });

    // Event listener to update the text display when the slider changes
    priceRange.noUiSlider.on('update', function (values, handle) {
        const minPrice = parseFloat(values[0].replace('£', '').replace(',', ''));
        const maxPrice = parseFloat(values[1].replace('£', '').replace(',', ''));

        priceRangeText.textContent =  values[0]+' - '+values[1];
        document.querySelectorAll('#flight-itemArrival').forEach(function (flightItem) {
            const flightPrice = parseFloat(flightItem.getAttribute('data-price'));

            if (flightPrice >= minPrice && flightPrice <= maxPrice) {
                flightItem.style.display = 'block';  // Show flight
            } else {
                flightItem.style.display = 'none';   // Hide flight
            }
        });
    });
});
document.addEventListener("DOMContentLoaded",function (){
    var ScheduleRange=document.getElementById("Schedule-range");
    var ScheduleStart=document.getElementById("ScheduleStart");
    var ScheduleEnd=document.getElementById("Schedule-range-end");
    var ScheduleEndText=document.getElementById("ScheduleEnd");
    var Timerange=document.getElementById("Time-range");
    var TimerangeText=document.getElementById("TimeRange");
    noUiSlider.create(Timerange, {
        start: [79],
        connect: [true,false],
        range: {
            'min': 0,
            'max': 79
        },
        step: 1,  // Step size for the slider
        tooltips: true,
        format: {
            to: function (value) {
                return Math.round(value);

            },
            from: function (value) {


                return parseInt(value, 10); // Convert from the formatted string
            }
        }
    });
    noUiSlider.create(ScheduleEnd, {
        start: [0, 24],
        connect: true,
        range: {
            'min': 0,
            'max': 24
        },
        step: 0.5,  // Step size for the slider
        tooltips: true, // Show the current value when the user drags the handle
        format: {
            to: function (value) {
                var hours = Math.floor(value);
                var minutes = Math.round((value - hours) * 60);


                if (minutes === 60) {
                    hours += 1;
                    minutes = 0;
                }
                return hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0');

            },
            from: function (value) {


                return value.replace('£', ''); // Convert from the formatted string
            }
        }
    });
    noUiSlider.create(ScheduleRange, {
        start: [0, 24],
        connect: true,
        range: {
            'min': 0,
            'max': 24
        },
        step: 0.5,  // Step size for the slider
        tooltips: true, // Show the current value when the user drags the handle
        format: {
            to: function (value) {
                var hours = Math.floor(value);
                var minutes = Math.round((value - hours) * 60);


                if (minutes === 60) {
                    hours += 1;
                    minutes = 0;
                }
                return hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0');

            },
            from: function (value) {


                return value.replace('£', ''); // Convert from the formatted string
            }
        }
    });
    ScheduleRange.noUiSlider.on('update', function (values, handle) {
        const start = parseFloat(values[0]);
        const end = parseFloat(values[1]);
        ScheduleStart.textContent="Go "+values[0]+" - "+values[1];
        document.querySelectorAll('#flight-itemArrival').forEach(function (flightItem) {
            const flightTime = flightItem.getAttribute('data-TimeDepart');
            const flightHour = convertTimeToDecimal(flightTime);
            if (flightHour >= start && flightHour <= end) {
                flightItem.style.display = 'block';
            } else {
                flightItem.style.display = 'none'; // Ẩn
            }

        });
    });
    Timerange.noUiSlider.on('update', function (values, handle) {
        TimerangeText.textContent='Dưới '+values[0]+' Tiếng';
        document.querySelectorAll('#flight-itemArrival').forEach(function (flightItem) {
            const flightTime =parseInt(flightItem.getAttribute('data-duration'));
            if(flightTime<=values[0]){
                flightItem.style.display='block'
            }else{
                flightItem.style.display='none'
            }


        });
    });
    ScheduleEnd.noUiSlider.on('update', function (values, handle) {
        const start = parseFloat(values[0]);
        const end = parseFloat(values[1]);
        ScheduleEndText.textContent="To "+values[0]+" - "+values[1];
        document.querySelectorAll('#flight-itemArrival').forEach(function (flightItem) {
            const flightTime = flightItem.getAttribute('data-timeArrival');

            const flightHour = convertTimeToDecimal(flightTime);
            if (flightHour >= start && flightHour <= end) {
                flightItem.style.display = 'block';
            } else {
                flightItem.style.display = 'none'; // Ẩn
            }

        });
    });
})
function initDepartTime() {
    initFlatpickr("#datePickerInput", {
        mode: "range", // Enable date range selection
        dateFormat: "Y-m-d", // Format to match LocalDate (yyyy-MM-dd)
        defaultDate: [new Date()], // Set default dates to today
        minDate: "today", // Chỉ cho phép chọn từ ngày hôm nay
        onReady: function (selectedDates, dateStr, instance) {
            const clearButton = document.createElement("button");
            clearButton.type = "button";
            clearButton.textContent = "Clear";
            clearButton.className = "flatpickr-clear-btn";
            clearButton.style.margin = "5px";
            clearButton.style.padding = "5px 10px";
            clearButton.style.backgroundColor = "#f44336";
            clearButton.style.color = "#fff";
            clearButton.style.border = "none";
            clearButton.style.cursor = "pointer";

            // Xóa các ngày đã chọn khi nhấn nút Clear
            clearButton.addEventListener("click", function () {
                instance.clear();
                instance.close(); // Đóng Flatpickr sau khi xóa
            });

            // Gắn nút Clear vào Flatpickr footer
            instance.calendarContainer.appendChild(clearButton);
            if (selectedDates.length) {
                const firstDate = instance.formatDate(selectedDates[0], "Y-m-d");
                instance.element.value = firstDate; // Set the input value to the first date
            }
        },
    });
}
function initArrivalTime() {
    const departTimeInput = document.getElementById("datePickerInput");
    let departDateValue = departTimeInput.value;

    // Nếu departtime chưa được chọn, sử dụng ngày mai
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(today.getDate() + 1);

    // Nếu departtime đã được chọn, tính minDate từ giá trị này
    if (departDateValue) {
        const departDate = new Date(departDateValue);
        departDate.setDate(departDate.getDate() + 1); // Phải lớn hơn departtime 1 ngày
        departDateValue = departDate.toISOString().split('T')[0];
    } else {
        departDateValue = tomorrow.toISOString().split('T')[0];
    }

    initFlatpickr("#datePickerOutput", {
        mode: "range", // Enable date range selection
        dateFormat: "Y-m-d", // Format to match LocalDate (yyyy-MM-dd)
        defaultDate: [new Date()], // Set default dates to today
        minDate: departDateValue, // Chỉ cho phép chọn từ ngày sau departtime
        onReady: function (selectedDates, dateStr, instance) {
            const clearButton = document.createElement("button");
            clearButton.type = "button";
            clearButton.textContent = "Clear";
            clearButton.className = "flatpickr-clear-btn";
            clearButton.style.margin = "5px";
            clearButton.style.padding = "5px 10px";
            clearButton.style.backgroundColor = "#f44336";
            clearButton.style.color = "#fff";
            clearButton.style.border = "none";
            clearButton.style.cursor = "pointer";
            clearButton.addEventListener("click", function () {
                instance.clear();
                instance.close(); // Đóng Flatpickr sau khi xóa
            });

            // Gắn nút Clear vào Flatpickr footer
            instance.calendarContainer.appendChild(clearButton);
            if (selectedDates.length) {
                const firstDate = instance.formatDate(selectedDates[0], "Y-m-d");
                instance.element.value = firstDate; // Set the input value to the first date
            }
        },
    });
}
document.getElementById("datePickerInput").addEventListener("focus", function () {
    // Initialize flatpickr only if not already initialized
    if (!this.classList.contains("flatpickr-input-active")) {
        initDepartTime();
        this.classList.add("flatpickr-input-active");
    }
});
document.getElementById("datePickerOutput").addEventListener("focus", function () {
    // Initialize flatpickr only if not already initialized
    if (!this.classList.contains("flatpickr-input-active")) {
        initArrivalTime();
        this.classList.add("flatpickr-input-active");
    }
});
document.addEventListener('DOMContentLoaded', async function () {
    const fromSearchDropdown = document.querySelector("#from-airport");
    const fromAirport = document.querySelector("#FromAirport");
    const ToSearch = document.querySelector("#To-Airport");
    const ToAirPort = document.querySelector("#ToAirPort");
    const loadingOverlay = document.createElement("div");
    loadingOverlay.className = "loading-overlay";

    // Spinner element
    const spinner = document.createElement("div");
    spinner.className = "spinner";
    loadingOverlay.appendChild(spinner);

    // Optional loading text
    const loadingText = document.createElement("div");
    loadingText.className = "loading-text";
    loadingText.textContent = "Loading, please wait...";
    loadingOverlay.appendChild(loadingText);

    // Add overlay to the body
    document.body.appendChild(loadingOverlay);

    // Create skeleton elements


    try {
        let fromAirports = await SearchById(fromAirport.value);
        let ToAirports = await SearchById(ToAirPort.value);
        if (fromAirports != null) {
            // Replace skeleton with the real content


            fromSearchDropdown.value = fromAirports.name;
            ToSearch.value = ToAirports.name;
        }
    } catch (error) {
        console.log(error);
    } finally {
        // Remove the loading overlay after the async operation
        document.body.removeChild(loadingOverlay);
    }
});

document.getElementById('from-airport').addEventListener('input',async (event)=>{
    const search=event.target.value;
    const dropdown=document.getElementById('from-dropdown');
    const airportList=document.getElementById('airport-list');
    if(search.trim()===''){
        dropdown.style.display='none';
        return;
    }
    try {
        const response=await fetch(`http://localhost:8686/api/AirPort/SearchAirPort?search=${encodeURIComponent(search)}`);
        if(response.ok){
            const airports=await response.json();
            airportList.innerHTML = "";
            airports.forEach(airport => {
                const li = document.createElement("li");

                // Set the dropdown header with the country of the airport
                li.innerHTML = `
                    <div class="dropdown-header" style="display: flex">
                        <span id="city-name">${airport.country}</span>
                        <span id="all-airports">Mọi sân bay</span>
                    </div>
                `;

                // Iterate over the airportDTOS and append each airport item
                airport.aiportDTOS.forEach(airportdto => {
                    const airportItem = document.createElement("div");
                    airportItem.classList.add("airport-item");
                    airportItem.innerHTML = `
                        <div class="airport-info">
                            <span class="airport-name"><i class="fa fa-plane icon" style="margin-right: 8px; color: #2a2a2a;"></i>Sân bay ${airportdto.name}</span>
                             <span class="airport-code">${airportdto.city.name}</span>
                        </div>
                    `;

                    // Add click event listener specifically to the 'airport-item' div
                    airport.aiportDTOS.forEach(airportdto => {
                        const airportItem = document.createElement("div");
                        airportItem.classList.add("airport-item");
                        airportItem.innerHTML = `
                        <div class="airport-info">
                            <span class="airport-name"><i class="fa fa-plane icon" style="margin-right: 8px; color: #2a2a2a;"></i>Sân bay ${airportdto.name}</span>
                            <span class="airport-code">${airportdto.city.name}</span>
                        </div>
                    `;

                        // Add click event listener specifically to the 'airport-item' div
                        airportItem.addEventListener("click", () => {
                            document.getElementById("from-input").value = `${airportdto.city.name}`;

                            // Store the actual airport.id in the hidden input
                            document.getElementById("from-input-id").value = airportdto.id;
                            const toInputValue = document.getElementById("to-input").value;
                            if (toInputValue === document.getElementById("from-input").value) {
                                Swal.fire({
                                    title:'Departure and arrival locations cannot be the same!',
                                    icon:'error'
                                });
                                document.getElementById("from-input-id").value=0;
                                document.getElementById('from-input').value=''
                                return; // Prevent the selection if they are the same
                            }
                            dropdown.style.display = "none";
                        });

                        li.appendChild(airportItem);
                    });

                    li.appendChild(airportItem);
                });

                airportList.appendChild(li);
            });
            dropdown.style.display = "block";
        }
    }catch (error){
        console.error("Error fetching airport data:", error);
    }
});

document.getElementById('To-Airport').addEventListener('input',async (event)=>{
    const search=event.target.value;
    const dropdown=document.getElementById('to-dropdown');
    const airportList=document.getElementById('airportListDropdown');
    if(search.trim()===''){
        dropdown.style.display='none';
        return;
    }
    try {
        const response=await fetch(`http://localhost:8686/api/AirPort/SearchAirPort?search=${encodeURIComponent(search)}`);
        if(response.ok){
            const airports=await response.json();
            airportList.innerHTML = "";
            airports.forEach(airport => {
                const li = document.createElement("li");

                // Set the dropdown header with the country of the airport
                li.innerHTML = `
                    <div class="dropdown-header" style="display: flex">
                        <span id="city-name">${airport.country}</span>
                        <span id="all-airports">Mọi sân bay</span>
                    </div>
                `;

                // Iterate over the airportDTOS and append each airport item
                airport.aiportDTOS.forEach(airportdto => {
                    const airportItem = document.createElement("div");
                    airportItem.classList.add("airport-item");
                    airportItem.innerHTML = `
                        <div class="airport-info">
                            <span class="airport-name"><i class="fa fa-plane icon" style="margin-right: 8px; color: #2a2a2a;"></i>Sân bay ${airportdto.name}</span>
                               <span class="airport-code">${airportdto.city.name}</span>
                        </div>
                    `;

                    // Add click event listener specifically to the 'airport-item' div
                    airportItem.addEventListener("click", () => {
                        document.getElementById("To-Airport").value = `${airportdto.name} (${airportdto.city.name})`;
                        document.getElementById("to-input-id").value = airportdto.id;
                        dropdown.style.display = "none"; // Hide dropdown after selection
                    });

                    li.appendChild(airportItem);
                });

                airportList.appendChild(li);
            });
            dropdown.style.display = "block";
        }
    }catch (error){
        console.error("Error fetching airport data:", error);
    }
})
document.addEventListener("click", (event) => {
    const fromDropdown = document.getElementById("from-dropdown");
    const toDropdown = document.getElementById("to-dropdown");
    const searchFrom = document.getElementById("from-airport");
    const searchTo = document.getElementById("To-Airport");

    // Ẩn dropdown "Bay từ" nếu nhấp ra ngoài
    if (!searchFrom.contains(event.target)) {
        fromDropdown.style.display = "none";
    }

    // Ẩn dropdown "Bay đến" nếu nhấp ra ngoài
    if (!searchTo.contains(event.target)) {
        toDropdown.style.display = "none";
    }
});
document.addEventListener('DOMContentLoaded', function () {
    const dropdownItems = document.querySelectorAll('.dropdown-item');
    const dropdownSelectedItem = document.getElementById('dropdownSelectedItem');
    const selectElement = document.getElementById('selectElement');

    // Event listener for dropdown item selection
    dropdownItems.forEach(item => {
        item.addEventListener('click', function (event) {
            const selectedText = event.target.textContent;
            const selectedValue = event.target.getAttribute('data-value');

            // Update dropdown button text
            dropdownSelectedItem.textContent = selectedText;

            // Update the <select> element selected option
            const options = selectElement.value;

            selectElement.value=selectedText;

            // Trigger selectpicker update (optional if using a library like bootstrap-select)

        });
    });
});
document.addEventListener('DOMContentLoaded',()=>{
    const flightDetailsLinks = document.querySelectorAll('.flight-details-link');
    const popup = document.getElementById('flight-details-popup');
    const popupContent = document.getElementById('flight-details-content');
    const closeButton = document.getElementById('close-popup');
    flightDetailsLinks.forEach(link=>{
        link.addEventListener('click',(event)=>{
            const flightId = link.getAttribute('data-idFlight');
            fetch(`http://localhost:8686/api/Flight/detail/${flightId}`)
                .then(response=>response.json())
                .then(data=>{

                    document.getElementById('durationString').textContent=data.durationString;
                    document.getElementById("img-popup").src="http://localhost:8686/images/flight/"+data.imageUrl;
                    document.getElementById('nameAirline').textContent=data.nameAirline;
                    document.getElementById('dateArrival').textContent=data.dateArrival;
                    document.getElementById('dateDepart').textContent=data.dateDepart;
                    document.getElementById('TimeArrival').textContent=data.timeArrival;
                    document.getElementById('DepartureTime').textContent=data.timeDepart;
                })
                .catch(error => console.error('Error fetching seat details:', error));
            event.preventDefault();
            popup.style.setProperty('display', 'block', 'important');
            popup.style.zIndex='1000';
            popupContent.style.setProperty('display', 'flex', 'important');
        })
    });
    closeButton.addEventListener('click', () => {
        popup.style.display = 'none';
        popupContent.style.display='none'
    });

})
function convertTimeToDecimal(time) {
    const [hours, minutes] = time.split(':').map(Number);
    return hours + (minutes / 60);
}
const ModalHeader=document.querySelector('#ModalHeader');


async function SearchById( id){
    try {
        const response=await fetch(`http://localhost:8686/api/AirPort/FindById/${encodeURIComponent(id)}`);
        let airports=null;
        if(response.ok){
            airports=await response.json();

        }else {
            console.error(`Error: ${response.status} - ${response.statusText}`);
        }
        return airports;
    }catch (error){
        console.log(error)
    }
}
document.addEventListener('DOMContentLoaded',function (){
    let flexBoxInputCheck = document.getElementById('flexSwitchCheckDefault');
    if (flexBoxInputCheck.checked) {
        document.getElementById('AtHotel').style.display = 'block';
        document.getElementById('TimeHotel').style.display='block';
        document.getElementById('RoomNumber').style.display='block';
        document.getElementById('btn-Submit').style.top='43vh';
    }else{
        document.getElementById('AtHotel').style.display = 'none';
        document.getElementById('TimeHotel').style.display='none';
        document.getElementById('RoomNumber').style.display='none';
        document.getElementById('btn-Submit').style.top='28vh';
    }
})
document.getElementById('flexSwitchCheckDefault').addEventListener('change',function (){
    const AtHotel=document.getElementById('AtHotel');
    const TimeHotel=document.getElementById('TimeHotel');
    const RoomNumber=document.getElementById('RoomNumber');
    if(this.checked){
        AtHotel.style.display='block';
        TimeHotel.style.display='block';
        RoomNumber.style.display='block';
        document.getElementById('btn-Submit').style.top='43vh'
    }else{
        AtHotel.style.display='none';
        TimeHotel.style.display='none';
        RoomNumber.style.display='none';

        document.getElementById('btn-Submit').style.top='28vh'
    }
})
document.getElementById('SearchBtn').addEventListener('click',function (event){

    ModalHeader.style.opacity='1';
    ModalBody.style.opacity='1';
    ModalContainer.style.display="block"
})
document.getElementById('closeBtn').addEventListener('click',function (event){
    event.preventDefault();
    ModalHeader.style.opacity='0';
    ModalBody.style.opacity='0';
    ModalContainer.style.display="none"
})
// Thêm sự kiện click cho nút dropdown


let selectedAirlines=[];
function updateFlightVisibility(){
    document.querySelectorAll('#flight-itemArrival').forEach(function(flightItem) {
        const airlineId = flightItem.getAttribute('data-airline-id');


        if (selectedAirlines.length === 0 || selectedAirlines.includes(airlineId)) {
            flightItem.style.display = 'block'; // Show flight
        } else {
            flightItem.style.display = 'none'; // Hide flight
        }
    });
}
document.getElementById('selectAllToggle').addEventListener('change',function (){
    const isChecked=this.checked;
    if(isChecked){
        selectedAirlines=[];
        document.querySelectorAll('.flight-select').forEach(function(checkbox) {
            const airlineId = checkbox.closest('.custom-control').querySelector('input').getAttribute('id').split('-')[1];
            selectedAirlines.push(airlineId);
            checkbox.checked = true;
        });
    }else{
        selectedAirlines = [];
        document.querySelectorAll('.flight-select').forEach(function(checkbox) {
            checkbox.checked = false;
        });
    }
    updateFlightVisibility();
})
document.querySelectorAll('.airline-checkbox').forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        const airlineId = this.id.split('-')[1];
        console.log(airlineId)
        if (this.checked) {
            selectedAirlines.push(airlineId); // Add airline ID to selected list
        } else {
            selectedAirlines = selectedAirlines.filter(function(id) {
                return id !== airlineId;
            });
        }

        updateFlightVisibility();

    });
});
document.addEventListener("DOMContentLoaded", function() {
    var priceRange = document.getElementById('price-range');
    var priceRangeText = document.getElementById('price-range-text');
    var minPrice = parseFloat(document.getElementById('minPrice').value) || 0;
    var maxPrice = parseFloat(document.getElementById('maxPrice').value) || 0;
    // Initialize the noUiSlider
    noUiSlider.create(priceRange, {
        start: [minPrice, maxPrice], // Set the default values for the range
        connect: true, // Connect the handles
        range: {
            'min': minPrice,    // Minimum value of the slider
            'max': maxPrice   // Maximum value of the slider
        },
        step: 10,  // Step size for the slider
        tooltips: true, // Show the current value when the user drags the handle
        format: {
            to: function (value) {
                return '£' + Math.round(value); // Format values as currency
            },
            from: function (value) {
                return value.replace('£', ''); // Convert from the formatted string
            }
        }
    });

    // Event listener to update the text display when the slider changes
    priceRange.noUiSlider.on('update', function (values, handle) {
        const minPrice = parseFloat(values[0].replace('£', '').replace(',', ''));
        const maxPrice = parseFloat(values[1].replace('£', '').replace(',', ''));

        priceRangeText.textContent =  values[0]+' - '+values[1];
        document.querySelectorAll('#flight-itemArrival').forEach(function (flightItem) {
            const flightPrice = parseFloat(flightItem.getAttribute('data-price'));

            if (flightPrice >= minPrice && flightPrice <= maxPrice) {
                flightItem.style.display = 'block';  // Show flight
            } else {
                flightItem.style.display = 'none';   // Hide flight
            }
        });
    });
});
document.addEventListener("DOMContentLoaded",function (){
    var ScheduleRange=document.getElementById("Schedule-range");
    var ScheduleStart=document.getElementById("ScheduleStart");
    var ScheduleEnd=document.getElementById("Schedule-range-end");
    var ScheduleEndText=document.getElementById("ScheduleEnd");
    var Timerange=document.getElementById("Time-range");
    var TimerangeText=document.getElementById("TimeRange");
    noUiSlider.create(Timerange, {
        start: [79],
        connect: [true,false],
        range: {
            'min': 0,
            'max': 79
        },
        step: 1,  // Step size for the slider
        tooltips: true,
        format: {
            to: function (value) {
                return Math.round(value);

            },
            from: function (value) {


                return parseInt(value, 10); // Convert from the formatted string
            }
        }
    });
    noUiSlider.create(ScheduleEnd, {
        start: [0, 24],
        connect: true,
        range: {
            'min': 0,
            'max': 24
        },
        step: 0.5,  // Step size for the slider
        tooltips: true, // Show the current value when the user drags the handle
        format: {
            to: function (value) {
                var hours = Math.floor(value);
                var minutes = Math.round((value - hours) * 60);


                if (minutes === 60) {
                    hours += 1;
                    minutes = 0;
                }
                return hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0');

            },
            from: function (value) {


                return value.replace('£', ''); // Convert from the formatted string
            }
        }
    });
    noUiSlider.create(ScheduleRange, {
        start: [0, 24],
        connect: true,
        range: {
            'min': 0,
            'max': 24
        },
        step: 0.5,  // Step size for the slider
        tooltips: true, // Show the current value when the user drags the handle
        format: {
            to: function (value) {
                var hours = Math.floor(value);
                var minutes = Math.round((value - hours) * 60);


                if (minutes === 60) {
                    hours += 1;
                    minutes = 0;
                }
                return hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0');

            },
            from: function (value) {


                return value.replace('£', ''); // Convert from the formatted string
            }
        }
    });
    ScheduleRange.noUiSlider.on('update', function (values, handle) {
        const start = parseFloat(values[0]);
        const end = parseFloat(values[1]);
        ScheduleStart.textContent="Go "+values[0]+" - "+values[1];
        document.querySelectorAll('#flight-itemArrival').forEach(function (flightItem) {
            const flightTime = flightItem.getAttribute('data-TimeDepart');
            const flightHour = convertTimeToDecimal(flightTime);
            if (flightHour >= start && flightHour <= end) {
                flightItem.style.display = 'block';
            } else {
                flightItem.style.display = 'none'; // Ẩn
            }

        });
    });
    Timerange.noUiSlider.on('update', function (values, handle) {
        TimerangeText.textContent='Below '+values[0]+' Hours';
        document.querySelectorAll('#flight-itemArrival').forEach(function (flightItem) {
            const flightTime =parseInt(flightItem.getAttribute('data-duration'));
            if(flightTime<=values[0]){
                flightItem.style.display='block'
            }else{
                flightItem.style.display='none'
            }


        });
    });
    ScheduleEnd.noUiSlider.on('update', function (values, handle) {
        const start = parseFloat(values[0]);
        const end = parseFloat(values[1]);
        ScheduleEndText.textContent="Arrive "+values[0]+" - "+values[1];
        document.querySelectorAll('#flight-itemArrival').forEach(function (flightItem) {
            const flightTime = flightItem.getAttribute('data-timeArrival');

            const flightHour = convertTimeToDecimal(flightTime);
            if (flightHour >= start && flightHour <= end) {
                flightItem.style.display = 'block';
            } else {
                flightItem.style.display = 'none'; // Ẩn
            }

        });
    });
})
function goBack() {
    // Kiểm tra nếu có lịch sử trang trước
    if (document.referrer) {
        window.history.back();
    } else {
        // Nếu không có trang trước, có thể điều hướng về trang mặc định hoặc trang chủ
        window.location.href = "/SearchFlight"; // Hoặc bất kỳ URL nào bạn muốn
    }
}

document.addEventListener("DOMContentLoaded", function() {
    // Get all the tab buttons
    const tabButtons = document.querySelectorAll('.nav-tabs .nav-link');
    const tabPanes = document.querySelector(`#TabPanel0`);
    const indexToActivate = 0;
    tabButtons[0].classList.add('active');

    tabPanes.classList.add('show','active')
    function activateTab(index) {
        // Remove 'active' class from all tabs and tab panes
        const tabPanes = document.querySelector(`#TabPanel${index}`);
        tabButtons.forEach(button => button.classList.remove('active'));
        tabPanes.classList.remove('show', 'active');

        // Add 'active' class to the clicked tab and corresponding tab pane
        tabButtons[index].classList.add('active');
        tabPanes.classList.add('show', 'active');
    }
    tabButtons.forEach((button, index) => {
        button.addEventListener('click', function() {
            const allTabPanes = document.querySelectorAll('.TabShow');
            allTabPanes.forEach(pane => pane.classList.remove('show', 'active'));
            const tabPane = document.querySelector(`#TabPanel${index}`);

            activateTab(index);
            tabPane.classList.add('show','active');
        });
    });
});


document.querySelectorAll('.dropdown-item').forEach(item => {
    item.addEventListener('click', function(e) {
        // Get the selected item text
        var selectedItem = this.textContent;

        // Update the button text to the selected item
        var button = this.closest('.dropdown').querySelector('.dropdown-toggle');
        button.querySelector('.filter-option-inner-inner').textContent = selectedItem;

        // Remove 'selected' class from all items and add it to the clicked one
        var dropdownItems = this.closest('.dropdown-menu').querySelectorAll('.dropdown-item');
        dropdownItems.forEach(i => i.classList.remove('selected'));
        this.classList.add('selected');
    });
});
async function SearchById( id){
    try {
        const response=await fetch(`http://localhost:8686/api/AirPort/FindById/${encodeURIComponent(id)}`);
        let airports=null;
        if(response.ok){
            airports=await response.json();

        }else {
            console.error(`Error: ${response.status} - ${response.statusText}`);
        }
        return airports;
    }catch (error){
        console.log(error)
    }
}

