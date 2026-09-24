import Alert from 'react-bootstrap/Alert'

interface StatusMessageProps {
	message: string | null
	variant?: 'danger' | 'success' | 'info'
}

const StatusMessage = (props: StatusMessageProps) => {
	if (!props.message) {
		return null
	}

	return <Alert variant={props.variant ?? 'danger'}>{props.message}</Alert>
}

export default StatusMessage
