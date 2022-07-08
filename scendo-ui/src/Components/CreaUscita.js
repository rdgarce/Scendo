import React, { useState } from "react";
import AuthService from "../Services/AuthService";
import UscitaService from "../Services/UscitaService";
import { useNavigate } from "react-router-dom";



const CreaUscita = () => {

  const navigate = useNavigate()

const [uscita, setUscita] = useState({
  tipoUscita: "",
	dataEOra: "",
	locationUscita: "",
	locationIncontro: "",
	uscitaPrivata: false,
	numeroMaxPartecipanti: "",
	descrizione: ""
});

const [errore, setErrore] = useState({
  messaggio: "",
});

const [successo, setSuccesso] = useState({
  messaggio: "",
});

const handleChange = (e) => {
  const value = e.target.value;
  setUscita({...uscita, [e.target.name]: value});
};

const handleCheck = (e) => {
  setUscita({...uscita, uscitaPrivata: !uscita.uscitaPrivata});
};

const salvaUscita = (e) => {
  e.preventDefault();
  UscitaService.creaUscita(uscita).then((response) => {   
    setErrore({...errore, messaggio: ""});
    setSuccesso({...successo, messaggio: "Uscita creata"});
    navigate("/home");
    //console.log(response.data.message);
}).catch((error) => {
    console.log(error);
    if(error.response.data.message){
        const value = error.response.data.message;
        setSuccesso({...successo, messaggio: ""})
        setErrore({...errore, messaggio: value});
    }
    if(error.response.status === 401){
      AuthService.logout();
      navigate("/login");
      navigate(0);
  }
});
}

  return (
    <div className="min-h-screen flex flex-col">
    <div className="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2">
        <div className="bg-white px-6 py-8 rounded shadow-md text-black w-full">
            <h1 className="mb-8 text-3xl text-center">Crea Uscita</h1>
            <input 
                type="text"
                className="block border border-grey-300 w-full p-3 rounded mb-4"
                onChange={(e) => handleChange(e)}
                name="tipoUscita"
                value={uscita.tipoUscita}
                placeholder="Tipo di uscita" />

            <input 
                type="text"
                className="block border border-grey-light w-full p-3 rounded mb-4"
                onChange={(e) => handleChange(e)}
                name="dataEOra"
                value={uscita.dataEOra}
                placeholder="Data" />

            <input 
                type="text"
                className="block border border-grey-light w-full p-3 rounded mb-4"
                onChange={(e) => handleChange(e)}
                name="locationUscita"
                value={uscita.locationUscita}
                placeholder="Location" />

            <input 
                type="text"
                className="block border border-grey-light w-full p-3 rounded mb-4"
                onChange={(e) => handleChange(e)}
                name="locationIncontro"
                value={uscita.locationIncontro}
                placeholder="Punto di incontro" />
            
              <div>
                <label>
                  <input
                    type = "checkbox"
                    className="p-3 mb-4"
                    onChange={(e) => handleCheck(e)}
                    name="uscitaPrivata"
                    label = "Uscita Privata"
                    value={uscita.uscitaPrivata}
                  />
                    Uscita Privata
                  </label>
              </div>


            <input 
                type="number"
                className="block border border-grey-light w-full p-3 rounded mb-4"
                onChange={(e) => handleChange(e)}
                name="numeroMaxPartecipanti"
                value={uscita.numeroMaxPartecipanti}
                placeholder="Numero massimo partecipanti" />

            <input 
                type="text"
                className="block border border-grey-light w-full p-3 rounded mb-4"
                onChange={(e) => handleChange(e)}
                name="descrizione"
                value={uscita.descrizione}
                placeholder="Descrizione" />

            <button
                onClick={salvaUscita}
                type="submit"
                className="w-full text-center p-3 rounded bg-green-400 text-white hover:bg-green-700 focus:outline-none my-1"
            >Crea Uscita</button>

            <div
                type="text"
                className="text-red-500"
            >{errore.messaggio} </div>

            <div
                type="text"
                className="text-green-400"
            >{successo.messaggio} </div>
        </div>
    </div>
    </div>
  )
}

export default CreaUscita