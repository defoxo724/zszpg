import { useState, type FormEvent } from 'react'
import type { RegistrationFieldErrors } from '../api/UserApi'

interface UserCredentialsFormProps {
	buttonLabel: string
	onSubmit: (login: string, password: string) => Promise<void>
	fieldErrors?: RegistrationFieldErrors
}

const UserCredentialsForm = ({
	buttonLabel,
	onSubmit,
	fieldErrors = {},
}: UserCredentialsFormProps) => {
	const [login, setLogin] = useState('')
	const [password, setPassword] = useState('')
	const [submitting, setSubmitting] = useState(false)

	const handleSubmit = async (event: FormEvent) => {
		event.preventDefault()
		setSubmitting(true)
		try {
			await onSubmit(login, password)
		} finally {
			setSubmitting(false)
		}
	}

	return (
		<form onSubmit={handleSubmit}>
			<div className="mb-3">
				<label className="form-label" htmlFor="login">
					Login
				</label>
				<input
					id="login"
					className={`form-control ${fieldErrors.login ? 'is-invalid' : ''}`}
					value={login}
					onChange={(event) => setLogin(event.target.value)}
					required
				/>
				{fieldErrors.login && <div className="invalid-feedback">{fieldErrors.login}</div>}
			</div>
			<div className="mb-3">
				<label className="form-label" htmlFor="password">
					Hasło
				</label>
				<input
					id="password"
					type="password"
					className={`form-control ${fieldErrors.password ? 'is-invalid' : ''}`}
					value={password}
					onChange={(event) => setPassword(event.target.value)}
					required
				/>
				{fieldErrors.password && (
					<div className="invalid-feedback">{fieldErrors.password}</div>
				)}
			</div>
			<button className="btn btn-primary w-100" disabled={submitting}>
				{submitting ? 'Proszę czekać...' : buttonLabel}
			</button>
		</form>
	)
}
export default UserCredentialsForm
