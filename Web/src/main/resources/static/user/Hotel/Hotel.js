document.getElementById("from-input").addEventListener('input',async (event)=>{
    const search = event.target.value;
    const dropdown = document.getElementById("from-dropdown");
    const airportList = document.getElementById("airport-list-from");

    if (search.trim() === "") {
        dropdown.style.display = "none";
        return;
    }
    try {
        const response=await fetch(`http://localhost:8686/api/AirPort/SearchAirPort?search=${encodeURIComponent(search)}`);
        console.log(response)
        if (response.ok) {
            const airports = await response.json();
            airportList.innerHTML = "";

            airports.forEach(airport => {
                const li = document.createElement("li");
                li.style.position='relative';
                li.style.left='-2vh'

                // Set the dropdown header with the country of the airport
                li.innerHTML = `
                    <div class="dropdown-header">
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
                        document.getElementById("from-input").value = `${airportdto.name} (${airportdto.code})`;

                        // Store the actual airport.id in the hidden input
                        document.getElementById("from-input-id").value = airportdto.id;

                        dropdown.style.display = "none";
                    });

                    li.appendChild(airportItem);
                });

                airportList.appendChild(li);
            });

            dropdown.style.display = "block";
        }
    }catch (error){
        console.log(error)
    }
});
document.addEventListener('click', (event) => {
    const dropdown = document.getElementById('At-dropdown');
    const input = document.getElementById('At-input');
    if (!dropdown.contains(event.target) && !input.contains(event.target)) {
        dropdown.style.display = "none";
    }
});

// Ngăn việc click bên trong dropdown bị đóng
document.getElementById('At-dropdown').addEventListener('click', (event) => {
    event.stopPropagation();
});
document.getElementById('At-input').addEventListener('input',async (event)=>{
    const search = event.target.value;
    const dropdown=document.getElementById('At-dropdown');
    const AtList=document.getElementById('At-List');
    if (search.trim() === "") {
        dropdown.style.display = "none";
        return;
    }
    try {
        const response=await fetch(`http://localhost:8686/api/city/SearchHotelByCityOrHotel?name=${encodeURIComponent(search)}`);
        if(response.ok){
            const airports = await response.json();
            AtList.innerHTML = "";
            airports.forEach(airport=>{

                const li = document.createElement("li");


                li.style.display='flex';
                li.style.justifyContent='space-between';
                li.style.alignItems='center'

                li.innerHTML = `
                    <i class="fa-solid fa-star"></i>
                    ${airport.name}, ${airport.country.name}
                  
                   <span class="popular">Phổ biến</span>
                `;
                li.addEventListener("click",()=>{
                    document.getElementById("At-input").value=`${airport.name}`;
                    document.getElementById('idCity').value=airport.id;
                    dropdown.style.display = "none";
                })
                AtList.appendChild(li);
            })
            dropdown.style.display = "block";
        }
    }catch (error){
        console.log(error)
    }
})

document.getElementById("to-input").addEventListener("focus", function () {
    this.select(); // Highlight all text when input is focused
});
document.getElementById("from-input").addEventListener("focus", function () {
    this.select(); // Highlight all text when input is focused
});
document.getElementById("to-input").addEventListener('input', async (event) => {
    const search = event.target.value;
    const dropdown = document.getElementById("to-dropdown");
    const airportList = document.getElementById("airport-list");

    if (search.trim() === "") {
        dropdown.style.display = "none";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8686/api/AirPort/SearchAirPort?search=${encodeURIComponent(search)}`);
        if (response.ok) {
            const airports = await response.json();
            airportList.innerHTML = "";

            airports.forEach(airport => {
                const li = document.createElement("li");

                // Set the dropdown header with the country of the airport
                li.innerHTML = `
                    <div class="dropdown-header">
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
                            <span class="airport-code">${airportdto.code}</span>
                        </div>
                    `;

                    // Add click event listener specifically to the 'airport-item' div
                    airportItem.addEventListener("click", () => {
                        document.getElementById("to-input").value = `${airportdto.name} (${airportdto.code})`;
                        document.getElementById("to-input-id").value = airportdto.id;
                        dropdown.style.display = "none"; // Hide dropdown after selection
                    });

                    li.appendChild(airportItem);
                });

                airportList.appendChild(li);
            });

            dropdown.style.display = "block";
        }
    } catch (error) {
        console.error("Error fetching airport data:", error);
    }
});

// Đóng dropdown khi nhấp ngoài
document.addEventListener("click", (event) => {
    const fromDropdown = document.getElementById("from-dropdown");
    const toDropdown = document.getElementById("to-dropdown");
    const searchFrom = document.getElementById("search-from");
    const searchTo = document.getElementById("search-to");

    // Ẩn dropdown "Bay từ" nếu nhấp ra ngoài
    if (!searchFrom.contains(event.target)) {
        fromDropdown.style.display = "none";
    }

    // Ẩn dropdown "Bay đến" nếu nhấp ra ngoài
    if (!searchTo.contains(event.target)) {
        toDropdown.style.display = "none";
    }
});
//Dropdown select




// Close dropdown if clicked outside

function initFlatpickr() {
    flatpickr("#datePickerInput", {
        mode: "range", // Enable date range selection
        dateFormat: "Y-m-d", // Format to match LocalDate (yyyy-MM-dd)
        defaultDate: [new Date()], // Set default dates in LocalDate format
        onReady: function (selectedDates, dateStr, instance) {
            if (selectedDates.length) {
                // Display only the first date in the range
                const firstDate = instance.formatDate(selectedDates[0], "Y-m-d");
                instance.element.value = firstDate; // Set the input value to the first date
            }
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (selectedDates.length) {
                // Update the input value to show only the first selected date
                const firstDate = instance.formatDate(selectedDates[0], "Y-m-d");
                instance.element.value = firstDate;
            }
        }
    });
}


document.getElementById("datePickerInput").addEventListener("focus", function () {
    // Initialize flatpickr only if not already initialized
    if (!this.classList.contains("flatpickr-input-active")) {
        initFlatpickr();
        this.classList.add("flatpickr-input-active");
    }
});
const ModalContainer=document.querySelector('#ModalContainer');
const ModalHeader=document.querySelector('#ModalHeader');
const ModalBody=document.querySelector('#ModalBody');
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

// Close dropdown if clicked outside

function initFlatpickr() {
    flatpickr("#datePickerInput", {
        mode: "range",  // Enable date range selection
        dateFormat: "M j / Y",  // Format for displayed dates
        defaultDate: ["Jul 7 / 2020", "Aug 25 / 2020"],  // Set default dates
        onReady: function (selectedDates, dateStr, instance) {
            instance.element.value = dateStr;
        }
    });
}
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





document.getElementById("datePickerInput").addEventListener("focus", function () {
    // Initialize flatpickr only if not already initialized
    if (!this.classList.contains("flatpickr-input-active")) {
        initFlatpickr();
        this.classList.add("flatpickr-input-active");
    }
});

document.addEventListener('click', function (event) {
    const isClickInside = document.getElementById('basicDropdownClickInvoker').contains(event.target) ||
        document.querySelector('#basicDropdownClick').contains(event.target);

    if (!isClickInside) {
        document.querySelector('#basicDropdownClick').classList.remove('show');
    }
})
document.addEventListener('DOMContentLoaded',async function (){
    const FromInputDropDown=document.querySelector('#from-input');
    const At=document.querySelector('#At-input');
    const City=document.querySelector('#idCity');
    const FromInput=document.querySelector('#from-input-id');
    const ArrivalInputDropDown=document.querySelector('#to-input');
    const ToInput=document.querySelector('#to-input-id');
    try {
   let fromAirports=await SearchById(FromInput.value);
   let ArrivalAirports=await SearchById(ToInput.value);
   let Cities=await SearchCityById(City.value);
   console.log(City.value)
   if(Cities!=null){
       At.value=Cities.name;
   }
   if(fromAirports!=null){
       FromInputDropDown.value=fromAirports.name;

   }
   if(ArrivalAirports!=null){
       ArrivalInputDropDown.value=ArrivalAirports.name;
   }
    }catch (error) {
        console.log(error);
    }
})
async function SearchCityById(id){
    try {
    const response=await fetch(`http://localhost:8686/api/city/FindById/${encodeURIComponent(id)}`);
    let cities=null;
    if(response.ok){
        cities=await response.json();
    }
    return cities;
    }catch (error){
        console.log(error)
    }
}
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