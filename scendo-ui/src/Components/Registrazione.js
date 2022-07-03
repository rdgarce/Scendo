import React, { useState } from "react";
import AuthService from "../Services/AuthService";

const Registrazione = () =>{

const [utente, setUtente] = useState({
    nome: "",
    cognome: "",
    dataDiNascita: "",
    sesso: "",
    email: "",
    password: "",
    cittaDiResidenza: "",
    codicePostale: "",
});

const [errore, setErrore] = useState({
    messaggio: "",
});

const [successo, setSuccesso] = useState({
    messaggio: "",
});

const handleChange = (e) => {
    const value = e.target.value;
    setUtente({...utente, [e.target.name]: value});
};

const salvaUtente = (e) => {
    e.preventDefault();
    AuthService.register(utente).then((response) => {   
        setErrore({...errore, messaggio: ""});
        setSuccesso({...successo, messaggio: "Registrazione avvenuta con successo"})
        console.log(response.data.message);

    }).catch((error) => {
        console.log(error);
        if(error.response.data.message){
            const value = error.response.data.message;
            setSuccesso({...successo, messaggio: ""})
            setErrore({...errore, messaggio: value});
        }
    });
}

return(
    <div className="bg-grey-lighter min-h-screen flex flex-col">
            <div className="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2">
                <div className="bg-white px-6 py-8 rounded shadow-md text-black w-full">
                    <h1 className="mb-8 text-3xl text-center">Registrazione</h1>
                    <input 
                        type="text"
                        className="block border border-grey-300 w-full p-3 rounded mb-4"
                        onChange={(e) => handleChange(e)}
                        name="nome"
                        value={utente.nome}
                        placeholder="Nome" />

                    <input 
                        type="text"
                        className="block border border-grey-light w-full p-3 rounded mb-4"
                        onChange={(e) => handleChange(e)}
                        name="cognome"
                        value={utente.cognome}
                        placeholder="Cognome" />

                    <input 
                        type="text"
                        className="block border border-grey-light w-full p-3 rounded mb-4"
                        onChange={(e) => handleChange(e)}
                        name="dataDiNascita"
                        value={utente.dataDiNascita}
                        placeholder="Data di Nascita" />

                    <input 
                        type="number"
                        className="block border border-grey-light w-full p-3 rounded mb-4"
                        onChange={(e) => handleChange(e)}
                        name="sesso"
                        value={utente.sesso}
                        placeholder="Sesso" />

                    <input 
                        type="text"
                        className="block border border-grey-light w-full p-3 rounded mb-4"
                        onChange={(e) => handleChange(e)}
                        name="email"
                        value={utente.email}
                        placeholder="Email" />

                    <input 
                        type="password"
                        className="block border border-grey-light w-full p-3 rounded mb-4"
                        onChange={(e) => handleChange(e)}
                        name="password"
                        value={utente.password}
                        placeholder="Password" />

                    <input 
                        type="text"
                        className="block border border-grey-light w-full p-3 rounded mb-4"
                        onChange={(e) => handleChange(e)}
                        name="cittaDiResidenza"
                        value={utente.cittaDiResidenza}
                        placeholder="Città di residenza" />

                    <input 
                        type="text"
                        className="block border border-grey-light w-full p-3 rounded mb-4"
                        onChange={(e) => handleChange(e)}
                        name="codicePostale"
                        value={utente.codicePostale}
                        placeholder="Codice Postale" />


                    <button
                        onClick={salvaUtente}
                        type="submit"
                        className="w-full text-center py-3 rounded bg-green-400 text-white hover:bg-green-700 focus:outline-none my-1"
                    >Crea Account</button>

                    <text
                        type="text"
                        className="text-red-500"
                    >{errore.messaggio} </text>

                    <text
                        type="text"
                        className="text-green-400"
                    >{successo.messaggio} </text>


                </div>

                <div className="text-grey-dark mt-6">
                    Hai già un account? 
                    <a className="no-underline border-b border-blue-600 text-blue-600" href="../login/">
                        Log in
                    </a>.
                </div>
            </div>
    </div>
);
};
export default Registrazione;