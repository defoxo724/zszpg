import { useState } from 'react'
import { Link, Navigate, useNavigate } from 'react-router-dom'
import { UserApi } from '../api/UserApi'
import UserCredentialsForm from '../components/UserCredentialsForm'
import StatusMessage from '../components/StatusMessage'
import { useAuth } from '../context/AuthContext'

const RegisterPage = () => {
	const { user } = useAuth()
	const navigate = useNavigate()
	const [error, setError] = useState<string | null>(null)

	if (user) {
		return <Navigate to="/meals" replace />
	}

	const handleRegister = async (login: string, password: string) => {
		try {
			await UserApi.register(login, password)
			navigate('/login', {
				replace: true,
			})
		} catch {
			setError('Nie udało się utworzyć konta. Login może być już zajęty.')
		}
	}

	return (
		<div className="card auth-card">
			<div className="card-body">
				<h1 className="h3 mb-3">Rejestracja</h1>
				<StatusMessage message={error} />
				<UserCredentialsForm buttonLabel="Utwórz konto" onSubmit={handleRegister} />
				<p className="mt-3 mb-0">
					Masz konto? <Link to="/login">Zaloguj się</Link>
				</p>
			</div>
		</div>
	)
}
export default RegisterPage
