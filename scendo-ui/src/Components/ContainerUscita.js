import React, { useState } from 'react'
import UscitaService from '../Services/UscitaService';

const ContainerUscita = ({uscita, reload, setReload}) => {

  const [email, setEmail] = useState("");

  const [errore, setErrore] = useState({
    messaggio: "",
  });
  
  const [successo, setSuccesso] = useState({
    messaggio: "",
  });

  const handleChange = (e) => {
    const value = e.target.value;
    setEmail(value);
  };

  const promuoviUtente = (e, email) => {
    e.preventDefault();
    UscitaService.promuoviUtente(uscita.message.idUscita, email).then((response) => {   
      setErrore({...errore, messaggio: ""});
      setSuccesso({...successo, messaggio: response.message});
      setReload(!reload);
      console.log(response.message);
  }).catch((error) => {
      console.log(error);
      if(error.response.data.message){
          const value = error.response.data.message;
          setSuccesso({...successo, messaggio: ""})
          setErrore({...errore, messaggio: value});
      }
  });
  }

  const invitaUtente = (e) => {
    e.preventDefault();
    UscitaService.invitaUtente(uscita.message.idUscita, email).then((response) => {
      //console.log(response.message);
      setSuccesso({...successo, messaggio: response.message});
      setErrore({...errore, messaggio: ""});
    }).catch((error) => {
        console.log(error);
        if(error.response.data.message){
          const value = error.response.data.message;
          setSuccesso({...successo, messaggio: ""})
          setErrore({...errore, messaggio: value});
      }
    });
  }

  return (
    <>
    <div
      type="text"
      className="text-red-500 text-center text-lg"
    >{errore.messaggio} </div>

    <div
      type="text"
      className="text-green-400 text-center text-lg"
    >{successo.messaggio} </div>
            
    <div className='container mx-auto my-8 shadow border-b'
        key={uscita.message.idUscita}>
            <div>
                 {"Tipo Uscita: \t" + uscita.message.tipoUscita}
            </div>
            <div>
                 {"Data: \t" + uscita.message.dataEOra.replace("T", " ").substring(0, 16)}
            </div>
            <div>
                 {"Location incontro: \t" + uscita.message.locationIncontro}
            </div>
            <div>
                 {"Location uscita: \t" + uscita.message.locationUscita}
            </div>
            <div>
                 {"Descrizione: \t" + uscita.message.descrizione}
            </div>
            <div className='flex'>
                <table className='max-w-md'>
                  <thead>
                    <tr>
                      <th className='text-left uppercase tracking-wider py-3 px-6'>
                        Nome
                      </th>
                      <th className='text-left uppercase tracking-wider py-3 px-6'>
                        Cognome
                      </th>
                      <th className='text-left uppercase tracking-wider py-3 px-6'>
                        Azioni
                      </th>
                    </tr>
                  </thead>
                  <tbody className='bg-white'>
                    {uscita.message.partecipanti.map((partecipante) => (
                    <tr key={partecipante.email}>
                      <td className='px-6'>{partecipante.nome}</td>
                      <td className='px-6'>{partecipante.cognome}</td>
                      <td>
                        <button
                          onClick={e => promuoviUtente(e, partecipante.email)}
                          type="submit"
                          className="w-full text-center px-6 rounded bg-green-400 text-white hover:bg-green-700 focus:outline-none my-1 disabled:bg-slate-400"
                          // disabilito il tasto promuovi se io non sono creatore o se l'utente della riga è già organizzatore/creatore
                          disabled={partecipante.utenteCreatore || partecipante.utenteOrganizzatore || 
                                    uscita.message.partecipanti.find( element => element.email === localStorage.getItem('email_utente') && element.utenteCreatore === false)
                         }
                          >Promuovi
                        </button>
                      </td>
                    </tr>
                    ))}
                    {uscita.message.partecipanti.find( element => element.email === localStorage.getItem('email_utente') && (
                    element.utenteCreatore === true || element.utenteOrganizzatore === true)) &&(
                    <tr>
                      <td>
                        <input 
                          type="text"
                          className="block border border-grey-light w-full px-2 rounded mb-4 mt-4"
                          onChange={(e) => handleChange(e)}
                          name="email"
                          value={email}
                          placeholder="Email" />
                      </td>
                      <td></td>
                      <td>
                        <button
                          onClick={invitaUtente}
                          type="submit"
                          className="w-full text-center px-6 rounded bg-green-400 text-white hover:bg-green-700 focus:outline-none my-1 disabled:bg-slate-400"
                          // disabilito il tasto promuovi se io non sono creatore o se l'utente della riga è già organizzatore/creatore
                          disabled={uscita.message.partecipanti.find( element => element.email === localStorage.getItem('email_utente') &&
                           element.utenteCreatore === false)
                          }
                          >Invita
                        </button>                      
                      </td>
                    </tr>
                    )}
                  </tbody>
                </table>
              </div>
    </div>
    </>
  )
}

export default ContainerUscita