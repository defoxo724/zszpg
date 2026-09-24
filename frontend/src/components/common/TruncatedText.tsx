import OverlayTrigger from 'react-bootstrap/OverlayTrigger'
import Tooltip from 'react-bootstrap/Tooltip'

interface TruncatedTextProps {
	text: string
	maxWidth?: string
}

const TruncatedText = (props: TruncatedTextProps) => (
	<OverlayTrigger placement="top" overlay={<Tooltip>{props.text}</Tooltip>}>
		<span
			className="truncated-text"
			style={{ maxWidth: props.maxWidth ?? '260px' }}
			tabIndex={0}
		>
			{props.text}
		</span>
	</OverlayTrigger>
)

export default TruncatedText
