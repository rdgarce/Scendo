import React from 'react'

const ContainerUscita = ({uscita}) => {
  return (
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
                <table className='max-w-xs'>
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
                          //onClick={promuoviUtente}
                          type="submit"
                          className="w-full text-center px-6 rounded bg-green-400 text-white hover:bg-green-700 focus:outline-none my-1"
                          >Promuovi
                        </button>
                      </td>
                    </tr>
                    ))}
                  </tbody>
                </table>
              </div>
    </div>
  )
}

export default ContainerUscita